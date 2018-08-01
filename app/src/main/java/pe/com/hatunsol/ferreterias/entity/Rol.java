package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Samuel on 08/10/2016.
 */

public class Rol {
    private int RolId;
    private String RolDes;

    public Rol(int rolId, String rolDes) {
        RolId = rolId;
        RolDes = rolDes;
    }

    public Rol() {
    }

    public int getRolId() {
        return RolId;
    }

    public void setRolId(int rolId) {
        RolId = rolId;
    }

    public String getRolDes() {
        return RolDes;
    }

    public void setRolDes(String rolDes) {
        RolDes = rolDes;
    }
}
