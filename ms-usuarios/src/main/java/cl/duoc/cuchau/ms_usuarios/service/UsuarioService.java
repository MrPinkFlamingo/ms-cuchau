package cl.duoc.cuchau.ms_usuarios.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.cuchau.ms_usuarios.model.Usuario;
import cl.duoc.cuchau.ms_usuarios.repository.UsuarioRepository;
import cl.duoc.cuchau.ms_usuarios.dto.LoginDTO;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // REGLAS DE NEGOCIO
    // 1. Consulta de todos los usuarios registrados
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // 2. Consulta de un usuario por su ID
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    // 3. Consulta de un usuario por su correo
    public Usuario obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + correo));
    }

    // 4. Registro de un nuevo usuario en la BD con validaciones
    public Usuario registrar(Usuario usuario) {
        if (usuario == null)
            throw new IllegalArgumentException("El usuario no puede ser nulo");

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty())
            throw new IllegalArgumentException("El nombre del usuario no puede ser nulo ni vacío");

        if (usuario.getRut() == null || usuario.getRut().trim().isEmpty())
            throw new IllegalArgumentException("El RUT del usuario es obligatorio");

        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty())
            throw new IllegalArgumentException("El correo no puede ser nulo ni vacío");

        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty())
            throw new IllegalArgumentException("La contraseña no puede ser nula ni vacía");

        // Evitar duplicados de correo o RUT
        if (usuarioRepository.existsByCorreo(usuario.getCorreo().trim()))
            throw new IllegalArgumentException("Ya existe un usuario registrado con ese correo electrónico");

        if (usuarioRepository.existsByRut(usuario.getRut().trim()))
            throw new IllegalArgumentException("Ya existe un usuario registrado con ese RUT");

        // Valores por defecto
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty())
            usuario.setRol("cliente");

        if (usuario.getEstado() == null || usuario.getEstado().trim().isEmpty())
            usuario.setEstado("Activo");

        return usuarioRepository.save(usuario);
    }

    // 5. Iniciar Sesión (Login) comprobando credenciales
    public Usuario login(LoginDTO loginDTO) {
        if (loginDTO == null)
            throw new IllegalArgumentException("Los datos de login no pueden ser nulos");

        if (loginDTO.getCorreo() == null || loginDTO.getCorreo().trim().isEmpty())
            throw new IllegalArgumentException("Debes ingresar tu correo");

        if (loginDTO.getContrasena() == null || loginDTO.getContrasena().trim().isEmpty())
            throw new IllegalArgumentException("Debes ingresar tu contraseña");

        Usuario usuario = usuarioRepository.findByCorreo(loginDTO.getCorreo().trim())
            .orElseThrow(() -> new RuntimeException("Credenciales incorrectas: Correo no registrado"));

        if (!usuario.getContrasena().equals(loginDTO.getContrasena()))
            throw new RuntimeException("Credenciales incorrectas: Contraseña errónea");

        if ("Inactivo".equalsIgnoreCase(usuario.getEstado()))
            throw new RuntimeException("Tu cuenta se encuentra inactiva. Contacta al administrador.");

        return usuario;
    }

    // 6. Actualización de un usuario existente
    public Usuario actualizar(Long id, Usuario nuevosDatos) {
        Usuario usuario = obtenerPorId(id);

        if (nuevosDatos.getNombre() != null && !nuevosDatos.getNombre().trim().isEmpty())
            usuario.setNombre(nuevosDatos.getNombre());

        if (nuevosDatos.getApellidos() != null)
            usuario.setApellidos(nuevosDatos.getApellidos());

        if (nuevosDatos.getContrasena() != null && !nuevosDatos.getContrasena().trim().isEmpty())
            usuario.setContrasena(nuevosDatos.getContrasena());

        if (nuevosDatos.getDireccion() != null)
            usuario.setDireccion(nuevosDatos.getDireccion());

        if (nuevosDatos.getCiudad() != null)
            usuario.setCiudad(nuevosDatos.getCiudad());

        if (nuevosDatos.getRegion() != null)
            usuario.setRegion(nuevosDatos.getRegion());

        if (nuevosDatos.getRol() != null && !nuevosDatos.getRol().trim().isEmpty())
            usuario.setRol(nuevosDatos.getRol());

        if (nuevosDatos.getEstado() != null && !nuevosDatos.getEstado().trim().isEmpty())
            usuario.setEstado(nuevosDatos.getEstado());

        return usuarioRepository.save(usuario);
    }

    // 7. Eliminación de un usuario por su ID
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id))
            throw new RuntimeException("Usuario no encontrado con id: " + id);

        usuarioRepository.deleteById(id);
    }
}
