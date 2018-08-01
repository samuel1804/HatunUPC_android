package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Samuel on 11/10/2016.
 */

public class Articulo {
private int IdArticulo;
    private String Nombre;
    private Categoria Categoria;
    private Marca Marca;
    private String UnidadMedida;
    private Double Cantidad;

    public int getIdArticulo() {
        return IdArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        IdArticulo = idArticulo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public pe.com.hatunsol.ferreterias.entity.Categoria getCategoria() {
        return Categoria;
    }

    public void setCategoria(pe.com.hatunsol.ferreterias.entity.Categoria categoria) {
        Categoria = categoria;
    }

    public pe.com.hatunsol.ferreterias.entity.Marca getMarca() {
        return Marca;
    }

    public void setMarca(pe.com.hatunsol.ferreterias.entity.Marca marca) {
        Marca = marca;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        UnidadMedida = unidadMedida;
    }

    public Double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Double cantidad) {
        Cantidad = cantidad;
    }
}
