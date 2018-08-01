package pe.com.hatunsol.ferreterias.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListDistrito {

    // http://www.hatunsol.com.pe/ws_hatun/Ubigeo.svc/Ubigeo?coddepa=15&codprov=01
    @SerializedName("ListarResult")
    List<Distrito> lista;

    public List<Distrito> getLista() {
        return lista;
    }

    public void setLista(List<Distrito> lista) {
        this.lista = lista;
    }
}
