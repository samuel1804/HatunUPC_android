package pe.com.hatunsol.ferreterias.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pe.com.hatunsol.ferreterias.entity.Parametro;

/**
 * Created by Sistemas on 27/02/2015.
 */
public class ListParametro {

    // Para distritos de Lima: http://ubigeo-peru.gopagoda.com/ubigeo/distritos/lima/lima
    // More info: https://github.com/jmcastagnetto/ubigeo-peru/blob/master/README.md
    @SerializedName("ListarResult")
    List<Parametro> lista;

    public List<Parametro> getLista() {
        return lista;
    }

    public void setLista(List<Parametro> lista) {
        this.lista = lista;
    }
}
