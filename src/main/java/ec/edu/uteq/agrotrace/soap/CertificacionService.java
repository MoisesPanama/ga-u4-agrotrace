package ec.edu.uteq.agrotrace.soap;

import ec.edu.uteq.agrotrace.lote.domain.EstadoLote;
import ec.edu.uteq.agrotrace.lote.domain.Finca;
import ec.edu.uteq.agrotrace.lote.domain.Lote;
import ec.edu.uteq.agrotrace.lote.domain.LoteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CertificacionService {

    private final LoteRepository loteRepository;
    private final AtomicLong contadorCertificados = new AtomicLong(1000);

    public CertificacionService(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    // TODO-GA-10: Logica de certificacion SOAP
    public CertificarLoteResponse certificar(String codigoLote, String cedulaTecnico) {
        Optional<Lote> loteOpt = loteRepository.findByCodigo(codigoLote);

        if (loteOpt.isEmpty()) {
            throw new RuntimeException("Lote no encontrado: " + codigoLote);
        }

        Lote lote = loteOpt.get();

        // Validar que el lote este en estado ACEPTADO
        if (lote.getEstado() != EstadoLote.ACEPTADO) {
            throw new RuntimeException(
                "El lote " + codigoLote + " no esta en estado ACEPTADO. Estado actual: " + lote.getEstado());
        }

        // Generar numero de certificado
        String numeroCertificado = "CERT-" + contadorCertificados.incrementAndGet();

        // Crear respuesta
        CertificarLoteResponse respuesta = new CertificarLoteResponse();
        respuesta.setNumeroCertificado(numeroCertificado);
        respuesta.setCodigoLote(codigoLote);
        respuesta.setFincaOrigen(lote.getFinca() != null ? lote.getFinca().getNombre() : "Sin finca");
        respuesta.setPesoKg(lote.getPesoKg());
        respuesta.setEstadoLote(lote.getEstado().name());
        respuesta.setFechaEmision(LocalDate.now());
        respuesta.setVigente(true);

        // Marcar lote como certificado
        lote.setCertificado(true);
        loteRepository.save(lote);

        return respuesta;
    }

    // TODO-GA-10: Consulta de lote
    public ConsultarLoteResponse consultar(String codigoLote) {
        Optional<Lote> loteOpt = loteRepository.findByCodigo(codigoLote);

        if (loteOpt.isEmpty()) {
            throw new RuntimeException("Lote no encontrado: " + codigoLote);
        }

        Lote lote = loteOpt.get();

        ConsultarLoteResponse respuesta = new ConsultarLoteResponse();
        respuesta.setCodigoLote(codigoLote);
        respuesta.setFincaOrigen(lote.getFinca() != null ? lote.getFinca().getNombre() : "Sin finca");
        respuesta.setProductor(lote.getFinca() != null ? lote.getFinca().getProductorNombre() : "N/A");
        respuesta.setPesoKg(lote.getPesoKg());
        respuesta.setHumedadPorcentaje(lote.getHumedadPorcentaje());
        respuesta.setFermentacionPorcentaje(lote.getFermentacionPorcentaje());
        respuesta.setEstadoLote(lote.getEstado().name());
        respuesta.setCertificado(lote.getCertificado());

        return respuesta;
    }
}
