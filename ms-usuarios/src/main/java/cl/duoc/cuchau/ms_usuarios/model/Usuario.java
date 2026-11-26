package cl.duoc.cuchau.ms_usuarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    //#region ATRIBUTOS
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
    private String rol = "cliente"; // "admin", "vendedor", "cliente"

    @Column(nullable = false, length = 20)
    private String estado = "Activo"; // "Activo", "Inactivo"

    @Column(length = 50)
    private String region;

    @Column(length = 50)
    private String ciudad;

    @Column(length = 300)
    private String direccion;
    //#endregion

    //#region CONSTRUCTORES
    public Usuario() {
    }

    public Usuario(Long id, String rut, String nombre, String apellidos, String fechaNacimiento,
                    String correo, String contrasena, String rol, String estado,
                    String region, String ciudad, String direccion) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = (rol != null && !rol.isBlank()) ? rol : "cliente";
        this.estado = (estado != null && !estado.isBlank()) ? estado : "Activo";
        this.region = region;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }
    //#endregion

    //#region GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    //#endregion
}
