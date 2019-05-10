public enum StatusResponse {

    SUCCESS ("Success"),
    ERROR ("Error"),
    EXIST ("Exist"),
    NOT_EXIST ("Not exist"),
    ID_ERROR ("ID invalid"),
    NAME_ERROR ("Name invalid"),
    PROPIETARIO_ERROR ("Propietario invalid"),
    NOT_REMOVE ("Not removed"),
    CLASIFICACION_ERROR ("Clasificacion invalid"),
    ESTADO_ERROR ("Estado invald"),
    DATE_ERROR ("Date invalid"),
    USUARIO_ERROR ("Usuario invalid"),
    NO_INCIDENT ("Incidente empty"),
    NO_PROJECT ("Proyecto invalido");

    private String status;

    StatusResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
