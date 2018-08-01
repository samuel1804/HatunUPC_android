package pe.com.hatunsol.ferreterias.model;

import com.google.gson.annotations.SerializedName;

public class Distrito {

    @SerializedName("_CodDist")
    private String codDistrito;
    @SerializedName("_CodDpto")
    private String codDepartament;
    @SerializedName("_CodProv")
    private String codProvincia;
    @SerializedName("_CodUbigeo")
    private String codUbigeo;
    @SerializedName("_Nombre")
    private String nombre;

    public String getCodUbigeo() {
        return codUbigeo;
    }

    public void setCodUbigeo(String codUbigeo) {
        this.codUbigeo = codUbigeo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodDistrito() {
        return codDistrito;
    }

    public void setCodDistrito(String codDistrito) {
        this.codDistrito = codDistrito;
    }

    public String getCodDepartament() {
        return codDepartament;
    }

    public void setCodDepartament(String codDepartament) {
        this.codDepartament = codDepartament;
    }

    public String getCodProvincia() {
        return codProvincia;
    }

    public void setCodProvincia(String codProvincia) {
        this.codProvincia = codProvincia;
    }

    @Override
    public String toString() {
        /*return "Distrito{reniec='" + reniec + '\'' +
                ", inei='" + inei + "\', nombre='" + nombre + '\'' +
                ", nombreCompleto='" + nombreCompleto + "\'}";*/
        return nombre;
    }

    public Distrito() {
    }

    public Distrito(String codUbigeo, String nombre) {
        this.codUbigeo = codUbigeo;
        this.nombre = nombre;
    }
}
