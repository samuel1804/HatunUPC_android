package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Sistemas on 22/09/2016.
 */
public class Rechazo {
    private int IdRechazo;
    private String Descripcion;

    public int getIdRechazo() {
        return IdRechazo;
    }

    public void setIdRechazo(int idRechazo) {
        IdRechazo = idRechazo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Rechazo(int idRechazo, String descripcion) {
        IdRechazo = idRechazo;
        Descripcion = descripcion;
    }

    public Rechazo() {

    }
    @Override
    public String toString() {
        return Descripcion;
    }
}
