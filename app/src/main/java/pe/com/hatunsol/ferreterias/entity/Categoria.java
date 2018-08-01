package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Samuel on 11/10/2016.
 */

public class Categoria {
    private int IdCategoria;
    private String Nombre;

    public int getIdCategoria() {
        return IdCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        IdCategoria = idCategoria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Categoria( String nombre) {

        Nombre = nombre;
    }
}
