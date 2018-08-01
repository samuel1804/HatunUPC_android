package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Sistemas on 05/10/2016.
 */
public class Obra {
    private int IdObra;
    private String Nombre;
    private String UnidadMedidaNombre;
    private String UnidadMedidaCorto;
    private String Foto;
    private String Descripcion;

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getUnidadMedidaCorto() {
        return UnidadMedidaCorto;
    }

    public void setUnidadMedidaCorto(String unidadMedidaCorto) {
        UnidadMedidaCorto = unidadMedidaCorto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getIdObra() {
        return IdObra;
    }

    public void setIdObra(int idObra) {
        IdObra = idObra;
    }



    public String getUnidadMedidaNombre() {
        return UnidadMedidaNombre;
    }

    public void setUnidadMedidaNombre(String unidadMedidaNombre) {
        UnidadMedidaNombre = unidadMedidaNombre;
    }

    public Obra(String nombre) {
        Nombre = nombre;
    }

    public Obra(String nombre, String unidadMedidaNombre) {
        Nombre = nombre;
        UnidadMedidaNombre = unidadMedidaNombre;
    }

    public Obra(int idObra, String nombre) {
        IdObra = idObra;
        Nombre = nombre;
    }

    public Obra(){}
}
