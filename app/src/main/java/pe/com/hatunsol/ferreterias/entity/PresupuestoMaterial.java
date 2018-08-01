package pe.com.hatunsol.ferreterias.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Samuel on 11/10/2016.
 */

public class PresupuestoMaterial {
    private int IdPresupuestoMaterial;
    private Establecimiento Establecimiento;
    private Obra Obra;
    private Date Fecha;
    private double SubTotal;
    private List<Articulo> Lista_Articulos;
    private double Area;

    public double getArea() {
        return Area;
    }

    public void setArea(double area) {
        Area = area;
    }

    public List<Articulo> getLista_Articulos() {
        return Lista_Articulos;
    }

    public void setLista_Articulos(List<Articulo> lista_Articulos) {
        Lista_Articulos = lista_Articulos;
    }

    public int getIdPresupuestoMaterial() {
        return IdPresupuestoMaterial;
    }

    public void setIdPresupuestoMaterial(int idPresupuestoMaterial) {
        IdPresupuestoMaterial = idPresupuestoMaterial;
    }

    public pe.com.hatunsol.ferreterias.entity.Establecimiento getEstablecimiento() {
        return Establecimiento;
    }

    public void setEstablecimiento(pe.com.hatunsol.ferreterias.entity.Establecimiento establecimiento) {
        Establecimiento = establecimiento;
    }

    public pe.com.hatunsol.ferreterias.entity.Obra getObra() {
        return Obra;
    }

    public void setObra(pe.com.hatunsol.ferreterias.entity.Obra obra) {
        Obra = obra;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double subTotal) {
        SubTotal = subTotal;
    }
}
