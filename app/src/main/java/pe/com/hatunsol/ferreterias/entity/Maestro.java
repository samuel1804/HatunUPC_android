package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Samuel on 12/10/2016.
 */

public class Maestro {
    private int IdMaestro;
    private String Nombre;
    private String ApeMaterno;
    private String ApePaterno;
    private String DNI;
    private String Especialidad;
    private Double Precio;
    private String Celular;
    private String Distrito;
    private int Dias;
    private double SubTotal;
    private int Calificacion;

    public Maestro(String nombre, String celular, String DNI) {
        Nombre = nombre;
        Celular = celular;
        this.DNI = DNI;
    }

    public Maestro() {
    }

    public int getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(int calificacion) {
        Calificacion = calificacion;
    }

    public int getDias() {
        return Dias;
    }

    public void setDias(int dias) {
        Dias = dias;
    }

    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double subTotal) {
        SubTotal = subTotal;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public int getIdMaestro() {
        return IdMaestro;
    }

    public void setIdMaestro(int idMaestro) {
        IdMaestro = idMaestro;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApeMaterno() {
        return ApeMaterno;
    }

    public void setApeMaterno(String apeMaterno) {
        ApeMaterno = apeMaterno;
    }

    public String getApePaterno() {
        return ApePaterno;
    }

    public void setApePaterno(String apePaterno) {
        ApePaterno = apePaterno;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(String especialidad) {
        Especialidad = especialidad;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }
}
