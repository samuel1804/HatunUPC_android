package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Sistemas on 03/03/2015.
 */
public class Establecimiento {
  //  @SerializedName("_Direccion")
    private int IdEstablecimiento;

    public int getIdEstablecimiento() {
        return IdEstablecimiento;
    }

    public void setIdEstablecimiento(int idEstablecimiento) {
        IdEstablecimiento = idEstablecimiento;
    }

    private String Direccion;
    private int IdSupervisor;
    private int LocalId;
    private String NombreComercial;
    private String RUC;
    private String Supervisor;
    private String NombreLocal;
    private String Foto;
    private String RazonSocial;
    private String Telefono;
private String Representante;
    private String DNIRepresentante;
    private int TipoProveedorId;
    private String Observacion;
    private int TipoOrigenId;
    private boolean IndicadorActivo;
    private String Ubigeo;

    public boolean isIndicadorActivo() {
        return IndicadorActivo;
    }

    public void setIndicadorActivo(boolean indicadorActivo) {
        IndicadorActivo = indicadorActivo;
    }

    public String getUbigeo() {
        return Ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        Ubigeo = ubigeo;
    }

    public int getTipoOrigenId() {
        return TipoOrigenId;
    }

    public void setTipoOrigenId(int tipoOrigenId) {
        TipoOrigenId = tipoOrigenId;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    private String Referencia;

    public int getTipoProveedorId() {
        return TipoProveedorId;
    }

    public void setTipoProveedorId(int tipoProveedorId) {
        TipoProveedorId = tipoProveedorId;
    }

    public String getDNIRepresentante() {
        return DNIRepresentante;
    }

    public void setDNIRepresentante(String DNIRepresentante) {
        this.DNIRepresentante = DNIRepresentante;
    }

    public String getRepresentante() {
        return Representante;
    }

    public void setRepresentante(String representante) {
        Representante = representante;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getNombreLocal() {
        return NombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        NombreLocal = nombreLocal;
    }

    public String getSupervisor() {
        return Supervisor;
    }

    public void setSupervisor(String supervisor) {
        Supervisor = supervisor;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    private String ProveedorNombre;
    private String Latitud;
    private String Longitud;

    public String getProveedorNombre() {
        return ProveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        ProveedorNombre = proveedorNombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public int getIdSupervisor() {
        return IdSupervisor;
    }

    public void setIdSupervisor(int idSupervisor) {
        IdSupervisor = idSupervisor;
    }

    public int getLocalId() {
        return LocalId;
    }

    public void setLocalId(int localId) {
        LocalId = localId;
    }

    public String getNombreComercial() {
        return NombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        NombreComercial = nombreComercial;
    }



    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    @Override
    public String toString() {
        /*return "Distrito{reniec='" + reniec + '\'' +
                ", inei='" + inei + "\', nombre='" + nombre + '\'' +
                ", nombreCompleto='" + nombreCompleto + "\'}";*/
        return NombreComercial;
    }

    public Establecimiento() {
    }

    public Establecimiento(int IdEstablecimiento,String direccion, String nombreComercial, String RUC, String razonSocial) {
        this.IdEstablecimiento=IdEstablecimiento;
        Direccion = direccion;
        NombreComercial = nombreComercial;
        this.RUC = RUC;
        RazonSocial = razonSocial;
    }

    public Establecimiento(String direccion, String nombreComercial,String telefono) {
        Direccion = direccion;
        NombreComercial = nombreComercial;
        Telefono=telefono;
    }

    public Establecimiento(String direccion, int idSupervisor, int localId, String nombreComercial, int proveedorId, int proveedorLocalId, String RUC, String proveedorNombre, String latitud, String longitud) {
        Direccion = direccion;
        IdSupervisor = idSupervisor;
        LocalId = localId;
        NombreComercial = nombreComercial;

        this.RUC = RUC;
        ProveedorNombre = proveedorNombre;
        Latitud = latitud;
        Longitud = longitud;
    }
}
