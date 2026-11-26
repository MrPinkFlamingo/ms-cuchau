package cl.duoc.cuchau.ms_ordenes.dto;

public class EstadoOrdenDTO {

    private String estado;

    public EstadoOrdenDTO() {
    }

    public EstadoOrdenDTO(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
