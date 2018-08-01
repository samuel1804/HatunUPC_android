package pe.com.hatunsol.ferreterias.entity;


/**
 * Created by Samuel on 05/02/2015.
 */
public class Parametro {
    private Integer ParametroId;
    private String NombreCorto;

 /*   public Parametro(Integer parametroId, String nombreCorto) {
        ParametroId = parametroId;
        NombreCorto = nombreCorto;
    }*/

    public Integer getParametroId() {
        return ParametroId;
    }

    public void setParametroId(Integer parametroId) {
        ParametroId = parametroId;
    }

    public String getNombreCorto() {
        return NombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        NombreCorto = nombreCorto;
    }

    @Override
    public String toString() {
        /*return "Distrito{reniec='" + reniec + '\'' +
                ", inei='" + inei + "\', nombre='" + nombre + '\'' +
                ", nombreCompleto='" + nombreCompleto + "\'}";*/
        return NombreCorto;
    }

    public Parametro(Integer parametroId, String nombreCorto) {
        ParametroId = parametroId;
        NombreCorto = nombreCorto;
    }

    public Parametro() {
    }
}
