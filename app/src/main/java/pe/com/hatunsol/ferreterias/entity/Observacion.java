package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Vladimir on 06/03/2015.
 */
public class Observacion {

    private int IdObservacion;
    private String Fecha;
    private int IdCliente;
    private String Estado;
    private String Observacion;
    private String strDiaAgenda;

    /*public Observacion(int idObservacion, String fecha, int idCliente, String estado, String observacion) {
        IdObservacion = idObservacion;
        Fecha = fecha;
        IdCliente = idCliente;
        Estado = estado;
        Observacion = observacion;
    }*/

    public Observacion(String fecha, String estado, String observacion,String strDiaAgenda) {
        Fecha = fecha;
        Estado = estado;
        Observacion = observacion;
        this.strDiaAgenda=strDiaAgenda;
    }

    public int getIdObservacion() {
        return IdObservacion;
    }

    public void setIdObservacion(int idObservacion) {
        IdObservacion = idObservacion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        IdCliente = idCliente;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }

    public String getStrDiaAgenda() {
        return strDiaAgenda;
    }

    public void setStrDiaAgenda(String strDiaAgenda) {
        this.strDiaAgenda = strDiaAgenda;
    }
}
