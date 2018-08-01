package pe.com.hatunsol.ferreterias.entity;

public class TipoEstablecimiento {

    private int id;
    private String tipoEstablecimiento;

    public TipoEstablecimiento(int id) {
        this.id = id;
    }

    public TipoEstablecimiento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(String tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        TipoEstablecimiento tipo = (TipoEstablecimiento) o;
        return this.getId() == tipo.getId() ? true : false;
    }

    @Override
    public String toString() {
        return tipoEstablecimiento;
    }
}
