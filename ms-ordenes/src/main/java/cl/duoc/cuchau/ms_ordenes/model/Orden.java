package cl.duoc.cuchau.ms_ordenes.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "ordenes")
public class Orden {

    //#region ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_orden", nullable = false, unique = true, length = 20)
    private String numeroOrden;

    @Column(nullable = false, length = 10)
    private String fecha; // Formato dd-MM-yyyy

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(nullable = false, length = 150)
    private String cliente;

    @Column(nullable = false, length = 12)
    private String rut;

    @Column(nullable = false)
    private Integer total;

    @Column(nullable = false, length = 20)
    private String estado = "Pendiente"; // "Pendiente", "Completada", "Cancelada"

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrden> detalles = new ArrayList<>();
    //#endregion

    //#region CONSTRUCTORES
    public Orden() {
    }

    public Orden(Long id, String numeroOrden, String fecha, Long usuarioId, String cliente,
                 String rut, Integer total, String estado, List<DetalleOrden> detalles) {
        this.id = id;
        this.numeroOrden = numeroOrden;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.cliente = cliente;
        this.rut = rut;
        this.total = total;
        this.estado = (estado != null && !estado.isBlank()) ? estado : "Pendiente";
        if (detalles != null) {
            this.detalles = detalles;
        }
    }
    //#endregion

    //#region GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroOrden() { return numeroOrden; }
    public void setNumeroOrden(String numeroOrden) { this.numeroOrden = numeroOrden; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<DetalleOrden> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrden> detalles) { this.detalles = detalles; }
    //#endregion
}
