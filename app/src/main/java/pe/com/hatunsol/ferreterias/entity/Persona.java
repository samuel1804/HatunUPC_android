package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Sistemas on 04/03/2015.
 */
public class Persona {
    private int PersonaId;
    private String DocumentoNum;
    private int LocalId;
    private int ProveedorLocalId;
    private String Nombre;
    private String ApePaterno;
    private String ApeMaterno;
    private String Telefonos;
    private int EstadoCivilId;
    private int CodigoUsuarioCreacion;
    private String Correo;
    private String Direccion;
    private String Referencia;
    private String Ubigeo;
    private int CargoId;
    private int IdSupervisor;
    private String EstadoProcesoNombre;
    private int ExpedienteCreditoId;
    private int TipoPersonaId;
    private int DatosDireccionId;
    private int ProcesoId;
    private String NombreCompleto;
    private int EstadoUrgencia;
    private int NumeroHijos;
    private String FechaNacimiento;
    private String Horario;
private int CasaPropia;
    private int TipoTrabajoId;
    private String TipoEstablecimientoNombre;
    private int TipoPuestoId;
    private String FechaIngresoLaboral;
    private String Cargo;
    private String CentroTrabajo;
    private int FormalidadTrabajoId;
    private String Ruc;
    private Double IngresoNeto;
    private int SustentoIngresoId;
    private int SexoId;
    private int DatosLaboralesId;
    private int SolicitudId;
    private int EstadoProcesoId;

    public int getEstadoProcesoId() {
        return EstadoProcesoId;
    }

    public void setEstadoProcesoId(int estadoProcesoId) {
        EstadoProcesoId = estadoProcesoId;
    }

    public int getDatosLaboralesId() {
        return DatosLaboralesId;
    }

    public void setDatosLaboralesId(int datosLaboralesId) {
        DatosLaboralesId = datosLaboralesId;
    }

    public int getSolicitudId() {
        return SolicitudId;
    }

    public void setSolicitudId(int solicitudId) {
        SolicitudId = solicitudId;
    }

    public int getSexoId() {
        return SexoId;
    }

    public void setSexoId(int sexoId) {
        SexoId = sexoId;
    }

    public Double getIngresoNeto() {
        return IngresoNeto;
    }

    public void setIngresoNeto(Double ingresoNeto) {
        IngresoNeto = ingresoNeto;
    }

    public int getSustentoIngresoId() {
        return SustentoIngresoId;
    }

    public void setSustentoIngresoId(int sustentoIngresoId) {
        SustentoIngresoId = sustentoIngresoId;
    }

    public String getRuc() {
        return Ruc;
    }

    public void setRuc(String ruc) {
        Ruc = ruc;
    }

    public int getFormalidadTrabajoId() {
        return FormalidadTrabajoId;
    }

    public void setFormalidadTrabajoId(int formalidadTrabajoId) {
        FormalidadTrabajoId = formalidadTrabajoId;
    }

    public String getCentroTrabajo() {
        return CentroTrabajo;
    }

    public void setCentroTrabajo(String centroTrabajo) {
        CentroTrabajo = centroTrabajo;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }

    public String getFechaIngresoLaboral() {
        return FechaIngresoLaboral;
    }

    public void setFechaIngresoLaboral(String fechaIngresoLaboral) {
        FechaIngresoLaboral = fechaIngresoLaboral;
    }

    public int getTipoPuestoId() {
        return TipoPuestoId;
    }

    public void setTipoPuestoId(int tipoPuestoId) {
        TipoPuestoId = tipoPuestoId;
    }

    public String getTipoEstablecimientoNombre() {
        return TipoEstablecimientoNombre;
    }

    public void setTipoEstablecimientoNombre(String tipoEstablecimientoNombre) {
        TipoEstablecimientoNombre = tipoEstablecimientoNombre;
    }

    public int getTipoTrabajoId() {
        return TipoTrabajoId;
    }

    public void setTipoTrabajoId(int tipoTrabajoId) {
        TipoTrabajoId = tipoTrabajoId;
    }

    public int getCasaPropia() {
        return CasaPropia;
    }

    public void setCasaPropia(int casaPropia) {
        CasaPropia = casaPropia;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public int getNumeroHijos() {
        return NumeroHijos;
    }

    public void setNumeroHijos(int numeroHijos) {
        NumeroHijos = numeroHijos;
    }

    public int getEstadoUrgencia() {
        return EstadoUrgencia;
    }

    public void setEstadoUrgencia(int estadoUrgencia) {
        EstadoUrgencia = estadoUrgencia;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        NombreCompleto = nombreCompleto;
    }

    public int getProcesoId() {
        return ProcesoId;
    }

    public void setProcesoId(int procesoId) {
        ProcesoId = procesoId;
    }

    public int getDatosDireccionId() {
        return DatosDireccionId;
    }

    public void setDatosDireccionId(int datosDireccionId) {
        DatosDireccionId = datosDireccionId;
    }

    public int getPersonaId() {
        return PersonaId;
    }

    public void setPersonaId(int personaId) {
        PersonaId = personaId;
    }

    public Persona() {
    }

    public int getExpedienteCreditoId() {
        return ExpedienteCreditoId;
    }

    public void setExpedienteCreditoId(int expedienteCreditoId) {
        ExpedienteCreditoId = expedienteCreditoId;
    }

    public String getEstadoProcesoNombre() {
        return EstadoProcesoNombre;
    }

    public void setEstadoProcesoNombre(String estadoProcesoNombre) {
        EstadoProcesoNombre = estadoProcesoNombre;
    }

    public int getIdSupervisor() {
        return IdSupervisor;
    }

    public void setIdSupervisor(int idSupervisor) {
        IdSupervisor = idSupervisor;
    }

    private String Obra;

    public String getObra() {
        return Obra;
    }

    public void setObra(String obra) {
        Obra = obra;
    }

    public int getCargoId() {
        return CargoId;
    }

    public void setCargoId(int cargoId) {
        CargoId = cargoId;
    }

    public String getUbigeo() {
        return Ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        Ubigeo = ubigeo;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public String getDocumentoNum() {
        return DocumentoNum;
    }

    public void setDocumentoNum(String documentoNum) {
        DocumentoNum = documentoNum;
    }

    public int getLocalId() {
        return LocalId;
    }

    public void setLocalId(int localId) {
        LocalId = localId;
    }

    public int getProveedorLocalId() {
        return ProveedorLocalId;
    }

    public void setProveedorLocalId(int proveedorLocalId) {
        ProveedorLocalId = proveedorLocalId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApePaterno() {
        return ApePaterno;
    }

    public void setApePaterno(String apePaterno) {
        ApePaterno = apePaterno;
    }

    public String getApeMaterno() {
        return ApeMaterno;
    }

    public void setApeMaterno(String apeMaterno) {
        ApeMaterno = apeMaterno;
    }

    public String getTelefonos() {
        return Telefonos;
    }

    public void setTelefonos(String telefonos) {
        Telefonos = telefonos;
    }

    public int getEstadoCivilId() {
        return EstadoCivilId;
    }

    public void setEstadoCivilId(int estadoCivilId) {
        EstadoCivilId = estadoCivilId;
    }

    public int getTipoPersonaId() {
        return TipoPersonaId;
    }

    public void setTipoPersonaId(int tipoPersonaId) {
        TipoPersonaId = tipoPersonaId;
    }

    public int getCodigoUsuarioCreacion() {
        return CodigoUsuarioCreacion;
    }

    public void setCodigoUsuarioCreacion(int codigoUsuarioCreacion) {
        CodigoUsuarioCreacion = codigoUsuarioCreacion;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }
}
