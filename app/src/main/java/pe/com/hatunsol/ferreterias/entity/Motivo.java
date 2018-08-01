package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Sistemas on 22/09/2016.
 */
public class Motivo {
    private int IdMotivo;
    private String Descripcion;

    public int getIdMotivo() {
        return IdMotivo;
    }

    public void setIdMotivo(int idMotivo) {
        IdMotivo = idMotivo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Motivo(int idMotivo, String descripcion) {
        IdMotivo = idMotivo;
        Descripcion = descripcion;
    }

    public Motivo() {

    }

    @Override
    public String toString() {
        return  Descripcion ;
    }
}
