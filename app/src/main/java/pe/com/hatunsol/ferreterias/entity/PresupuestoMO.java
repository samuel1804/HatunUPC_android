package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by EfrainSamuelFloresHe on 29/11/2016.
 */

public class PresupuestoMO {

    private int IdPresupuestoMO;
    private Maestro maestro;
    private int Dias;
    private Double SubTotal;
    private Obra obra;
    private int Area;


    public int getIdPresupuestoMO() {
        return IdPresupuestoMO;
    }

    public void setIdPresupuestoMO(int idPresupuestoMO) {
        IdPresupuestoMO = idPresupuestoMO;
    }

    public Maestro getMaestro() {
        return maestro;
    }

    public void setMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    public int getDias() {
        return Dias;
    }

    public void setDias(int dias) {
        Dias = dias;
    }

    public Double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(Double subTotal) {
        SubTotal = subTotal;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public int getArea() {
        return Area;
    }

    public void setArea(int area) {
        Area = area;
    }
}
