package com.uteq.agrotrace.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "certificarLoteResponse", namespace = "http://uteq.com/agrotrace/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class CertificarLoteResponse {

    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private Long loteId;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private String codigo;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private String estado;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private String mensaje;
    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private Boolean exito;

    public Long getLoteId() { return loteId; }
    public void setLoteId(Long loteId) { this.loteId = loteId; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Boolean getExito() { return exito; }
    public void setExito(Boolean exito) { this.exito = exito; }
}
