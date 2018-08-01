package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Vladimir on 03/03/2015.
 */
public class Cliente {
    private int Idcliente;
    private String Nombre;
    private String Apellido;
    private String Direccion;
    private String DNI;
    private String Distrito;
    private String Proceso;
    private String Banco;
    private String Observacion;

    public Cliente() {

    }

    public Cliente(int idcliente, String nombre, String apellido, String direccion, String DNI, String distrito, String proceso, String banco, String observacion) {
        Idcliente = idcliente;
        Nombre = nombre;
        Apellido = apellido;
        Direccion = direccion;
        this.DNI = DNI;
        Distrito = distrito;
        Proceso = proceso;
        Banco = banco;
        Observacion = observacion;
    }

    public int getIdcliente() {
        return Idcliente;
    }

    public void setIdcliente(int idcliente) {
        Idcliente = idcliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public String getProceso() {
        return Proceso;
    }

    public void setProceso(String proceso) {
        Proceso = proceso;
    }

    public String getBanco() {
        return Banco;
    }

    public void setBanco(String banco) {
        Banco = banco;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }
}
