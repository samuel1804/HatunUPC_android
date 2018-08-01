package pe.com.hatunsol.ferreterias.entity;

import java.io.Serializable;

public class Local implements Serializable {
private int LocalId;
    private String LocalNombre;
    private String ZonaNombre;
    private String Descripcion;

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getLocalId() {
        return LocalId;
    }

    public void setLocalId(int localId) {
        LocalId = localId;
    }

    public String getLocalNombre() {
        return LocalNombre;
    }

    public void setLocalNombre(String localNombre) {
        LocalNombre = localNombre;
    }

    public String getZonaNombre() {
        return ZonaNombre;
    }

    public void setZonaNombre(String zonaNombre) {
        ZonaNombre = zonaNombre;
    }

    @Override
    public String toString() {
        /*return "Distrito{reniec='" + reniec + '\'' +
                ", inei='" + inei + "\', nombre='" + nombre + '\'' +
                ", nombreCompleto='" + nombreCompleto + "\'}";*/
        return Descripcion;
    }

    public Local(int localId, String descripcion) {
        LocalId = localId;
        Descripcion = descripcion;
    }

    public Local() {
    }
}
