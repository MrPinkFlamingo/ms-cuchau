package cl.duoc.cuchau.ms_ordenes.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.cuchau.ms_ordenes.model.DetalleOrden;
import cl.duoc.cuchau.ms_ordenes.model.Orden;
import cl.duoc.cuchau.ms_ordenes.repository.OrdenRepository;

@Service
public class OrdenService {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final Set<String> ESTADOS_VALIDOS = Set.of("Pendiente", "Completada", "Cancelada");

    @Autowired
    private OrdenRepository ordenRepository;

    // REGLAS DE NEGOCIO
    // 1. Consulta de todas las órdenes registradas
    public List<Orden> obtenerTodas() {
        return ordenRepository.findAll();
    }

    // 2. Consulta de una orden por su ID
    public Orden obtenerPorId(Long id) {
        return ordenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Orden no encontrada con id: " + id));
    }

    // 3. Consulta de una orden por su número de orden (ej: ORD-10045)
    public Orden obtenerPorNumeroOrden(String numeroOrden) {
        return ordenRepository.findByNumeroOrden(numeroOrden)
            .orElseThrow(() -> new RuntimeException("Orden no encontrada con número: " + numeroOrden));
    }

    // 4. Consulta de órdenes por RUT del cliente
    public List<Orden> obtenerPorRut(String rut) {
        return ordenRepository.findByRut(rut);
    }

    // 5. Consulta de órdenes por ID de usuario
    public List<Orden> obtenerPorUsuarioId(Long usuarioId) {
        return ordenRepository.findByUsuarioId(usuarioId);
    }

    // 6. Consulta de órdenes por estado
    public List<Orden> obtenerPorEstado(String estado) {
        return ordenRepository.findByEstadoIgnoreCase(estado);
    }

    // 7. Creación de una nueva orden de compra con validaciones
    public Orden crear(Orden orden) {
        if (orden == null)
            throw new IllegalArgumentException("La orden no puede ser nula");

        if (orden.getCliente() == null || orden.getCliente().trim().isEmpty())
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");

        if (orden.getRut() == null || orden.getRut().trim().isEmpty())
            throw new IllegalArgumentException("El RUT del cliente es obligatorio");

        // Generar número de orden si no viene especificado
        if (orden.getNumeroOrden() == null || orden.getNumeroOrden().trim().isEmpty()) {
            orden.setNumeroOrden(generarNumeroOrden());
        } else if (ordenRepository.existsByNumeroOrden(orden.getNumeroOrden().trim())) {
            throw new IllegalArgumentException("Ya existe una orden con el número: " + orden.getNumeroOrden());
        }

        // Asignar fecha actual si no viene especificada
        if (orden.getFecha() == null || orden.getFecha().trim().isEmpty()) {
            orden.setFecha(LocalDate.now().format(FORMATO_FECHA));
        }

        // Estado por defecto
        if (orden.getEstado() == null || orden.getEstado().trim().isEmpty()) {
            orden.setEstado("Pendiente");
        } else {
            validarEstado(orden.getEstado());
        }

        // Vincular detalles y calcular subtotales / total si corresponde
        int totalCalculado = 0;
        if (orden.getDetalles() != null && !orden.getDetalles().isEmpty()) {
            for (DetalleOrden detalle : orden.getDetalles()) {
                if (detalle.getNombreProducto() == null || detalle.getNombreProducto().trim().isEmpty())
                    throw new IllegalArgumentException("Cada detalle debe indicar el nombre del producto");
                if (detalle.getCantidad() == null || detalle.getCantidad() <= 0)
                    throw new IllegalArgumentException("La cantidad del producto debe ser mayor a 0");
                if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario() < 0)
                    throw new IllegalArgumentException("El precio unitario no puede ser negativo");

                int subtotal = detalle.getCantidad() * detalle.getPrecioUnitario();
                detalle.setSubtotal(subtotal);
                detalle.setOrden(orden);
                totalCalculado += subtotal;
            }
        }

        if (orden.getTotal() == null || orden.getTotal() <= 0) {
            if (totalCalculado > 0) {
                orden.setTotal(totalCalculado);
            } else {
                throw new IllegalArgumentException("El total de la orden debe ser mayor a 0");
            }
        }

        return ordenRepository.save(orden);
    }

    // 8. Actualización de una orden existente
    public Orden actualizar(Long id, Orden nuevosDatos) {
        Orden orden = obtenerPorId(id);

        if (nuevosDatos.getCliente() != null && !nuevosDatos.getCliente().trim().isEmpty())
            orden.setCliente(nuevosDatos.getCliente());

        if (nuevosDatos.getRut() != null && !nuevosDatos.getRut().trim().isEmpty())
            orden.setRut(nuevosDatos.getRut());

        if (nuevosDatos.getFecha() != null && !nuevosDatos.getFecha().trim().isEmpty())
            orden.setFecha(nuevosDatos.getFecha());

        if (nuevosDatos.getUsuarioId() != null)
            orden.setUsuarioId(nuevosDatos.getUsuarioId());

        if (nuevosDatos.getTotal() != null && nuevosDatos.getTotal() > 0)
            orden.setTotal(nuevosDatos.getTotal());

        if (nuevosDatos.getEstado() != null && !nuevosDatos.getEstado().trim().isEmpty()) {
            validarEstado(nuevosDatos.getEstado());
            orden.setEstado(normalizarEstado(nuevosDatos.getEstado()));
        }

        return ordenRepository.save(orden);
    }

    // 9. Cambio rápido de estado de la orden
    public Orden cambiarEstado(Long id, String nuevoEstado) {
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty())
            throw new IllegalArgumentException("El nuevo estado es obligatorio");

        validarEstado(nuevoEstado);
        Orden orden = obtenerPorId(id);
        orden.setEstado(normalizarEstado(nuevoEstado));
        return ordenRepository.save(orden);
    }

    // 10. Eliminación de una orden por su ID
    public void eliminar(Long id) {
        if (!ordenRepository.existsById(id))
            throw new RuntimeException("Orden no encontrada con id: " + id);

        ordenRepository.deleteById(id);
    }

    private void validarEstado(String estado) {
        boolean valido = ESTADOS_VALIDOS.stream().anyMatch(e -> e.equalsIgnoreCase(estado.trim()));
        if (!valido) {
            throw new IllegalArgumentException("Estado inválido. Valores permitidos: Pendiente, Completada, Cancelada");
        }
    }

    private String normalizarEstado(String estado) {
        return ESTADOS_VALIDOS.stream()
            .filter(e -> e.equalsIgnoreCase(estado.trim()))
            .findFirst()
            .orElse(estado.trim());
    }

    private String generarNumeroOrden() {
        String sufijo = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "ORD-" + sufijo;
    }
}
