package ec.edu.uteq.agrotrace.soap;

import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.WebServiceTemplateBuilder;
import org.springframework.ws.soap.client.core.SoapActionCallback;

// TODO-GA-11: Cliente del servicio SOAP de certificacion
@Service
public class ClienteCertificacion {

    private static final String NS = "https://agrotrace.uteq.edu.ec/soap/certificacion";

    private final WebServiceTemplate plantilla;

    public ClienteCertificacion(WebServiceTemplateBuilder constructor) {
        this.plantilla = constructor
                .setDefaultUri("http://localhost:8080/ws")
                .build();
    }

    public CertificarLoteResponse certificar(String codigoLote, String cedula) {
        CertificarLoteRequest peticion = new CertificarLoteRequest();
        peticion.setCodigoLote(codigoLote);
        peticion.setCedulaTecnico(cedula);

        return (CertificarLoteResponse) plantilla.marshalSendAndReceive(
                peticion,
                new SoapActionCallback(NS + "/Certificar"));
    }

    public ConsultarLoteResponse consultar(String codigoLote) {
        ConsultarLoteRequest peticion = new ConsultarLoteRequest();
        peticion.setCodigoLote(codigoLote);

        return (ConsultarLoteResponse) plantilla.marshalSendAndReceive(
                peticion,
                new SoapActionCallback(NS + "/Consultar"));
    }
}
