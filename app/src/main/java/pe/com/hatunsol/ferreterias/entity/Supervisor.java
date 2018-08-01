package pe.com.hatunsol.ferreterias.entity;

public class Supervisor {

    private int IdSupervisor;
    private String Nombre;
    private String Telefono;
    private String LocalNombre;
    private String Foto;

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getLocalNombre() {
        return LocalNombre;
    }

    public void setLocalNombre(String localNombre) {
        LocalNombre = localNombre;
    }

    public int getIdSupervisor() {
        return IdSupervisor;
    }

    public void setIdSupervisor(int idSupervisor) {
        IdSupervisor = idSupervisor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Override
    public String toString() {
        return Nombre;
    }

    public Supervisor(int idSupervisor, String nombre) {
        IdSupervisor = idSupervisor;
        Nombre = nombre;
    }

    public Supervisor() {
    }
}
