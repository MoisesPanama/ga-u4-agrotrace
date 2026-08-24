package com.uteq.agrotrace.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "consultarLoteRequest", namespace = "http://uteq.com/agrotrace/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarLoteRequest {

    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private String codigo;

    public ConsultarLoteRequest() {}

    public ConsultarLoteRequest(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}
