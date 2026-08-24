package com.uteq.agrotrace.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "consultarLoteResponse", namespace = "http://uteq.com/agrotrace/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarLoteResponse {

    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private Long id;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private String codigo;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private String productorNombre;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private String variedad;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private BigDecimal pesoKg;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private BigDecimal humedadPorcentaje;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private Integer fermentacionHoras;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private String estado;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private Boolean certificado;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private String mensaje;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private Boolean exito;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getProductorNombre() { return productorNombre; }
    public void setProductorNombre(String productorNombre) { this.productorNombre = productorNombre; }

    public String getVariedad() { return variedad; }
    public void setVariedad(String variedad) { this.variedad = variedad; }

    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }

    public BigDecimal getHumedadPorcentaje() { return humedadPorcentaje; }
    public void setHumedadPorcentaje(BigDecimal humedadPorcentaje) { this.humedadPorcentaje = humedadPorcentaje; }

    public Integer getFermentacionHoras() { return fermentacionHoras; }
    public void setFermentacionHoras(Integer fermentacionHoras) { this.fermentacionHoras = fermentacionHoras; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Boolean getCertificado() { return certificado; }
    public void setCertificado(Boolean certificado) { this.certificado = certificado; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Boolean getExito() { return exito; }
    public void setExito(Boolean exito) { this.exito = exito; }
}
