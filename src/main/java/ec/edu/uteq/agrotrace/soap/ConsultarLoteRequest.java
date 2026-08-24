package ec.edu.uteq.agrotrace.soap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "ConsultarLoteRequest", namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarLoteRequest {

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion", required = true)
    private String codigoLote;

    public ConsultarLoteRequest() {}

    public ConsultarLoteRequest(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }
}
