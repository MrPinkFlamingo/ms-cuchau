package cl.duoc.cuchau.ms_ordenes.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cl.duoc.cuchau.ms_ordenes.dto.EstadoOrdenDTO;
import cl.duoc.cuchau.ms_ordenes.model.Orden;
import cl.duoc.cuchau.ms_ordenes.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "http://localhost:5173") // Permite peticiones desde React (Vite)
@Tag(name = "Órdenes", description = "Operaciones de creación, consulta y gestión de órdenes de compra de Repuestos Cuchau")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    // 1. Listar todas las órdenes
    @GetMapping
    @Operation(summary = "Listar todas las órdenes", description = "Obtiene la lista completa de órdenes de compra generadas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    public List<Orden> listarOrdenes() {
        return ordenService.obtenerTodas();
    }

    // 2. Obtener orden por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden por ID", description = "Busca y devuelve una orden según su ID numérico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orden encontrada"),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    public Orden obtenerPorId(@PathVariable Long id) {
        return ordenService.obtenerPorId(id);
    }

    // 3. Obtener orden por número de orden
    @GetMapping("/numero/{numeroOrden}")
    @Operation(summary = "Obtener orden por número", description = "Busca una orden por su código (ej: ORD-10045)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orden encontrada"),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    public Orden obtenerPorNumeroOrden(@PathVariable String numeroOrden) {
        return ordenService.obtenerPorNumeroOrden(numeroOrden);
    }

    // 4. Listar órdenes por RUT del cliente
    @GetMapping("/rut/{rut}")
    @Operation(summary = "Listar órdenes por RUT", description = "Obtiene todas las órdenes asociadas al RUT de un cliente")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    public List<Orden> listarPorRut(@PathVariable String rut) {
        return ordenService.obtenerPorRut(rut);
    }

    // 5. Listar órdenes por ID de usuario
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar órdenes por usuario", description = "Obtiene todas las órdenes realizadas por un usuario específico")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    public List<Orden> listarPorUsuario(@PathVariable Long usuarioId) {
        return ordenService.obtenerPorUsuarioId(usuarioId);
    }

    // 6. Listar órdenes por estado
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar órdenes por estado", description = "Filtra órdenes por estado (Pendiente, Completada, Cancelada)")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    public List<Orden> listarPorEstado(@PathVariable String estado) {
        return ordenService.obtenerPorEstado(estado);
    }

    // 7. Crear nueva orden
    @PostMapping
    @Operation(summary = "Crear nueva orden", description = "Registra una nueva orden de compra en la BD de Repuestos Cuchau")
    @ApiResponse(responseCode = "200", description = "Orden creada exitosamente")
    public Orden crearOrden(@RequestBody Orden orden) {
        return ordenService.crear(orden);
    }

    // 8. Actualizar orden existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar orden por su ID", description = "Modifica los datos de una orden existente")
    public Orden actualizarOrden(@PathVariable Long id, @RequestBody Orden nuevosDatos) {
        return ordenService.actualizar(id, nuevosDatos);
    }

    // 9. Cambiar estado de una orden
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de la orden", description = "Actualiza el estado de la orden (Pendiente, Completada, Cancelada)")
    public Orden cambiarEstado(@PathVariable Long id, @RequestBody EstadoOrdenDTO estadoDTO) {
        return ordenService.cambiarEstado(id, estadoDTO.getEstado());
    }

    // 10. Eliminar orden
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar orden por su ID", description = "Elimina una orden del sistema")
    public void eliminarOrden(@PathVariable Long id) {
        ordenService.eliminar(id);
    }
}
