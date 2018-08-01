package pe.com.hatunsol.ferreterias.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pe.com.hatunsol.ferreterias.entity.Establecimiento;

public class ListProveedorLocal {

    // http://www.hatunsol.com.pe/ws_hatun/Ubigeo.svc/Ubigeo?coddepa=15&codprov=01
    @SerializedName("ListarResult")
    List<Establecimiento> lista;

    public List<Establecimiento> getLista() {
        return lista;
    }

    public void setLista(List<Establecimiento> lista) {
        this.lista = lista;
    }
}
