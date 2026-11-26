package cl.duoc.cuchau.ms_usuarios.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.cuchau.ms_usuarios.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // region Métodos de Búsqueda
    // Para iniciar sesión: buscar usuario por su correo electrónico
    Optional<Usuario> findByCorreo(String correo);

    // Para buscar usuario por su RUT chileno
    Optional<Usuario> findByRut(String rut);

    // Para el registro: comprobar si el correo ya está registrado para no duplicarlo
    boolean existsByCorreo(String correo);

    // Para el registro: comprobar si el RUT ya está registrado
    boolean existsByRut(String rut);
    // endregion
}
