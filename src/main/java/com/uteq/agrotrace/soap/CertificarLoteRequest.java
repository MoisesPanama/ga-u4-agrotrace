package com.uteq.agrotrace.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "certificarLoteRequest", namespace = "http://uteq.com/agrotrace/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class CertificarLoteRequest {

    @XmlElement(namespace = "http://uteq.com/agrotrace/soap")
    private Long loteId;

    public CertificarLoteRequest() {}

    public CertificarLoteRequest(Long loteId) {
        this.loteId = loteId;
    }

    public Long getLoteId() { return loteId; }
    public void setLoteId(Long loteId) { this.loteId = loteId; }
}
