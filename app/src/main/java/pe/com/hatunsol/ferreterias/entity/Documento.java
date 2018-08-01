package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Sistemas on 09/03/2016.
 */
public class Documento {
    private String TipoDocumentoAdjuntoNombre;
    private String Ruta;
    private String Tamanio;
    private String Formato;
    private String Nombre;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    private int AdjuntoId;

    public int getAdjuntoId() {
        return AdjuntoId;
    }

    public void setAdjuntoId(int adjuntoId) {
        AdjuntoId = adjuntoId;
    }

    public String getTipoDocumentoAdjuntoNombre() {
        return TipoDocumentoAdjuntoNombre;
    }

    public void setTipoDocumentoAdjuntoNombre(String tipoDocumentoAdjuntoNombre) {
        TipoDocumentoAdjuntoNombre = tipoDocumentoAdjuntoNombre;
    }

    public String getRuta() {
        return Ruta;
    }

    public void setRuta(String ruta) {
        Ruta = ruta;
    }

    public String getTamanio() {
        return Tamanio;
    }

    public void setTamanio(String tamanio) {
        Tamanio = tamanio;
    }

    public String getFormato() {
        return Formato;
    }

    public void setFormato(String formato) {
        Formato = formato;
    }
}
