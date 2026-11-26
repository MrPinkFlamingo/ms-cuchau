package cl.duoc.cuchau.ms_ordenes.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.cuchau.ms_ordenes.model.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    // region Métodos de Búsqueda
    // Buscar orden por su código legible (ej: ORD-10045)
    Optional<Orden> findByNumeroOrden(String numeroOrden);

    // Listar órdenes asociadas a un RUT de cliente
    List<Orden> findByRut(String rut);

    // Listar órdenes asociadas a un usuario por su ID
    List<Orden> findByUsuarioId(Long usuarioId);

    // Listar órdenes filtradas por estado (Pendiente, Completada, Cancelada)
    List<Orden> findByEstadoIgnoreCase(String estado);

    // Verificar si ya existe una orden con ese número
    boolean existsByNumeroOrden(String numeroOrden);
    // endregion
}
