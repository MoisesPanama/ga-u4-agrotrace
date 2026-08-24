package ec.edu.uteq.agrotrace.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

// TODO-GA-10: Endpoint SOAP para certificacion
@Endpoint
public class CertificacionEndpoint {

    private static final String NS = "https://agrotrace.uteq.edu.ec/soap/certificacion";

    private final CertificacionService servicio;

    public CertificacionEndpoint(CertificacionService servicio) {
        this.servicio = servicio;
    }

    @PayloadRoot(namespace = NS, localPart = "CertificarLoteRequest")
    @ResponsePayload
    public CertificarLoteResponse certificar(
            @RequestPayload CertificarLoteRequest peticion) {
        return servicio.certificar(
                peticion.getCodigoLote(), peticion.getCedulaTecnico());
    }

    @PayloadRoot(namespace = NS, localPart = "ConsultarLoteRequest")
    @ResponsePayload
    public ConsultarLoteResponse consultar(
            @RequestPayload ConsultarLoteRequest peticion) {
        return servicio.consultar(peticion.getCodigoLote());
    }
}
