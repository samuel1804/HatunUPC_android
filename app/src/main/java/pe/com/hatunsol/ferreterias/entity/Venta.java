package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by vladimir.flores on 10/03/2015.
 */
public class Venta {
    private int IdVenta;
    private String Fecha;
    private int IdFerreteria;
    private double Total;

    public Venta(int idVenta, String fecha, int idFerreteria, double total) {
        IdVenta = idVenta;
        Fecha = fecha;
        IdFerreteria = idFerreteria;
        Total = total;
    }

    public Venta() {

    }

    public int getIdVenta() {
        return IdVenta;
    }

    public void setIdVenta(int idVenta) {
        IdVenta = idVenta;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public int getIdFerreteria() {
        return IdFerreteria;
    }

    public void setIdFerreteria(int idFerreteria) {
        IdFerreteria = idFerreteria;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }
}
