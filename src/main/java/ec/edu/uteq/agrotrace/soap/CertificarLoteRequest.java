package ec.edu.uteq.agrotrace.soap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "CertificarLoteRequest", namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
@XmlAccessorType(XmlAccessType.FIELD)
public class CertificarLoteRequest {

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion", required = true)
    private String codigoLote;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion", required = true)
    private String cedulaTecnico;

    public CertificarLoteRequest() {}

    public CertificarLoteRequest(String codigoLote, String cedulaTecnico) {
        this.codigoLote = codigoLote;
        this.cedulaTecnico = cedulaTecnico;
    }

    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }

    public String getCedulaTecnico() { return cedulaTecnico; }
    public void setCedulaTecnico(String cedulaTecnico) { this.cedulaTecnico = cedulaTecnico; }
}
