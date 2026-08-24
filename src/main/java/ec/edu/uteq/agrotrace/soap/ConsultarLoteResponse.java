package ec.edu.uteq.agrotrace.soap;

import jakarta.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "ConsultarLoteResponse", namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarLoteResponse {

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private String codigoLote;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private String fincaOrigen;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private String productor;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private BigDecimal pesoKg;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private BigDecimal humedadPorcentaje;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private BigDecimal fermentacionPorcentaje;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private String estadoLote;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private Boolean certificado;

    public ConsultarLoteResponse() {}

    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }

    public String getFincaOrigen() { return fincaOrigen; }
    public void setFincaOrigen(String fincaOrigen) { this.fincaOrigen = fincaOrigen; }

    public String getProductor() { return productor; }
    public void setProductor(String productor) { this.productor = productor; }

    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }

    public BigDecimal getHumedadPorcentaje() { return humedadPorcentaje; }
    public void setHumedadPorcentaje(BigDecimal humedadPorcentaje) { this.humedadPorcentaje = humedadPorcentaje; }

    public BigDecimal getFermentacionPorcentaje() { return fermentacionPorcentaje; }
    public void setFermentacionPorcentaje(BigDecimal fermentacionPorcentaje) { this.fermentacionPorcentaje = fermentacionPorcentaje; }

    public String getEstadoLote() { return estadoLote; }
    public void setEstadoLote(String estadoLote) { this.estadoLote = estadoLote; }

    public Boolean getCertificado() { return certificado; }
    public void setCertificado(Boolean certificado) { this.certificado = certificado; }
}
