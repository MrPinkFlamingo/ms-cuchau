package cl.duoc.cuchau.ms_usuarios.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cl.duoc.cuchau.ms_usuarios.model.Usuario;
import cl.duoc.cuchau.ms_usuarios.dto.LoginDTO;
import cl.duoc.cuchau.ms_usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173") // Permite peticiones desde React (Vite)
@Tag(name = "Usuarios", description = "Operaciones de registro, autenticación y gestión de usuarios de Repuestos Cuchau")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 1. Listar todos los usuarios
    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene la lista completa de usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    // 2. Obtener usuario por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Busca y devuelve un usuario según su ID numérico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    // 3. Registrar nuevo usuario
    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo usuario", description = "Registra un nuevo cliente en la BD de Repuestos Cuchau")
    @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrar(usuario);
    }

    // 4. Iniciar sesión (Login)
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida el correo y contraseña del usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso"),
        @ApiResponse(responseCode = "400", description = "Credenciales incorrectas")
    })
    public Usuario login(@RequestBody LoginDTO loginDTO) {
        return usuarioService.login(loginDTO);
    }

    // 5. Actualizar datos de usuario
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario por su ID", description = "Modifica los datos de un usuario existente")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario nuevosDatos) {
        return usuarioService.actualizar(id, nuevosDatos);
    }

    // 6. Eliminar usuario
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario por su ID", description = "Elimina un usuario del sistema")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}
