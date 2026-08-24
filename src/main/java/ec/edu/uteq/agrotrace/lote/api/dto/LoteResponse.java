package ec.edu.uteq.agrotrace.lote.api.dto;

import ec.edu.uteq.agrotrace.lote.domain.EstadoLote;
import ec.edu.uteq.agrotrace.lote.domain.Lote;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record LoteResponse(
    Long id,
    String codigo,
    String fincaNombre,
    String productorNombre,
    LocalDate fechaRecepcion,
    BigDecimal pesoKg,
    BigDecimal humedadPorcentaje,
    BigDecimal fermentacionPorcentaje,
    EstadoLote estado,
    Boolean certificado,
    LocalDateTime createdAt
) {
    public static LoteResponse desde(Lote lote) {
        return new LoteResponse(
            lote.getId(),
            lote.getCodigo(),
            lote.getFinca() != null ? lote.getFinca().getNombre() : null,
            lote.getFinca() != null ? lote.getFinca().getProductorNombre() : null,
            lote.getFechaRecepcion(),
            lote.getPesoKg(),
            lote.getHumedadPorcentaje(),
            lote.getFermentacionPorcentaje(),
            lote.getEstado(),
            lote.getCertificado(),
            lote.getCreatedAt()
        );
    }
}
