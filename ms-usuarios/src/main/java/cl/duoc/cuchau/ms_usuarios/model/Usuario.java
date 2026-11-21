package cl.duoc.cuchau.ms_usuarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    // region 1. Atributos / Columnas de la Base de Datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 12)
    private String rut;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(name = "fecha_nacimiento", length = 10)
    private String fechaNacimiento;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false, length = 20)
    private String rol = "CLIENTE"; // "ADMIN", "VENDEDOR", "CLIENTE"

    @Column(length = 50)
    private String region;

    @Column(length = 50)
    private String ciudad;

    @Column(length = 300)
    private String direccion;
    // endregion

    // region 2. Constructores
    // Constructor vacío exigido por JPA
    public Usuario() {
    }

    // Constructor con todos los campos
    public Usuario(Long id, String rut, String nombre, String apellidos, String fechaNacimiento,
    String correo, String contrasena, String rol, String region, String ciudad, String direccion) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = (rol != null && !rol.isBlank()) ? rol : "CLIENTE";
        this.region = region;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }
    // endregion

    // region 3. Getters
    public Long getId() {
        return id;
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getRol() {
        return rol;
    }

    public String getRegion() {
        return region;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }
    // endregion

    // region 4. Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    // endregion

}
