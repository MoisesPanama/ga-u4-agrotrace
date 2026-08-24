package ec.edu.uteq.agrotrace.soap;

import jakarta.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "CertificarLoteResponse", namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
@XmlAccessorType(XmlAccessType.FIELD)
public class CertificarLoteResponse {

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private String numeroCertificado;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private String codigoLote;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private String fincaOrigen;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private BigDecimal pesoKg;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private String estadoLote;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private LocalDate fechaEmision;

    @XmlElement(namespace = "https://agrotrace.uteq.edu.ec/soap/certificacion")
    private Boolean vigente;

    public CertificarLoteResponse() {}

    public String getNumeroCertificado() { return numeroCertificado; }
    public void setNumeroCertificado(String numeroCertificado) { this.numeroCertificado = numeroCertificado; }

    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }

    public String getFincaOrigen() { return fincaOrigen; }
    public void setFincaOrigen(String fincaOrigen) { this.fincaOrigen = fincaOrigen; }

    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }

    public String getEstadoLote() { return estadoLote; }
    public void setEstadoLote(String estadoLote) { this.estadoLote = estadoLote; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public Boolean getVigente() { return vigente; }
    public void setVigente(Boolean vigente) { this.vigente = vigente; }
}
