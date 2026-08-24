package com.uteq.agrotrace.soap;

import com.uteq.agrotrace.service.CertificacionService;
import com.uteq.agrotrace.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigDecimal;
import java.util.Map;

// TODO-GA-09: Implementar servicio SOAP con contrato WSDL
@Endpoint
public class CertificacionSoapEndpoint {

    private static final String NAMESPACE_URI = "http://uteq.com/agrotrace/soap";

    @Autowired
    private CertificacionService certificacionService;

    @Autowired
    private LoteService loteService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "certificarLoteRequest")
    @ResponsePayload
    public CertificarLoteResponse certificarLote(@RequestPayload CertificarLoteRequest request) {
        CertificarLoteResponse response = new CertificarLoteResponse();

        try {
            Map<String, Object> resultado = certificacionService.certificarLote(request.getLoteId());
            response.setLoteId(request.getLoteId());
            response.setCodigo((String) resultado.get("codigo"));
            response.setEstado((String) resultado.get("estado"));
            response.setMensaje((String) resultado.get("mensaje"));
            response.setExito(true);
        } catch (RuntimeException e) {
            response.setLoteId(request.getLoteId());
            response.setEstado("ERROR");
            response.setMensaje(e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "consultarLoteRequest")
    @ResponsePayload
    public ConsultarLoteResponse consultarLote(@RequestPayload ConsultarLoteRequest request) {
        ConsultarLoteResponse response = new ConsultarLoteResponse();

        try {
            var lote = loteService.buscarPorCodigo(request.getCodigo())
                    .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

            response.setId(lote.getId());
            response.setCodigo(lote.getCodigo());
            response.setProductorNombre(lote.getProductorNombre());
            response.setVariedad(lote.getVariedad());
            response.setPesoKg(lote.getPesoKg());
            response.setHumedadPorcentaje(lote.getHumedadPorcentaje());
            response.setFermentacionHoras(lote.getFermentacionHoras());
            response.setEstado(lote.getEstado());
            response.setCertificado(lote.getCertificado());
            response.setExito(true);
        } catch (RuntimeException e) {
            response.setEstado("ERROR");
            response.setMensaje(e.getMessage());
            response.setExito(false);
        }

        return response;
    }
}
