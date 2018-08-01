package pe.com.hatunsol.ferreterias.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Samuel on 27/11/2016.
 */

public class Solicitud {
private int IdSolicitud;

    public String getFechaSolicitud() {
        return FechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        FechaSolicitud = fechaSolicitud;
    }

    private double MontoEfectivoProp;

    public double getMontoEfectivoProp() {
        return MontoEfectivoProp;
    }

    public void setMontoEfectivoProp(double montoEfectivoProp) {
        MontoEfectivoProp = montoEfectivoProp;
    }

    public double getMontoMaterialProp() {

        return MontoMaterialProp;
    }

    public void setMontoMaterialProp(double montoMaterialProp) {
        MontoMaterialProp = montoMaterialProp;
    }

    private double MontoMaterialProp;
    private String FechaSolicitud;
    private String Obra;
    private List<Persona> Personas;
    private String EstadoProceso;

    public String getEstadoProceso() {
        return EstadoProceso;
    }

    public void setEstadoProceso(String estadoProceso) {
        EstadoProceso = estadoProceso;
    }

    public int getIdSolicitud() {
        return IdSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        IdSolicitud = idSolicitud;
    }







    public String getObra() {
        return Obra;
    }

    public void setObra(String obra) {
        Obra = obra;
    }

    public List<Persona> getPersonas() {
        return Personas;
    }

    public void setPersonas(List<Persona> personas) {
        Personas = personas;
    }
}
