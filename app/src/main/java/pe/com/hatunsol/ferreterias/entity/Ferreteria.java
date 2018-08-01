package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Vladimir on 24/02/2015.
 */
public class Ferreteria {
    private int IdFerreteria;
    private String Nombre;
    private String Descripcion;


    /*public Ferreteria(int idferreteria,String nombre, String descripcion) {
        IdFerreteria = idferreteria;
        Nombre = nombre;
        Descripcion = descripcion;
    }*/

    public Ferreteria() {

    }

    public int getIdFerreteria() {
        return IdFerreteria;
    }

    public void setIdFerreteria(int idFerreteria) {
        IdFerreteria = idFerreteria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }


}
