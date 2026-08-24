package ec.edu.uteq.agrotrace.lote.api.dto;

import ec.edu.uteq.agrotrace.lote.domain.Finca;
import ec.edu.uteq.agrotrace.lote.domain.Lote;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CrearLoteRequest(
    @NotNull(message = "El peso es obligatorio")
    @DecimalMin(value = "1.0", message = "Minimo 1 kg")
    BigDecimal pesoKg,

    @DecimalMin(value = "0.0", message = "Minimo 0%")
    @DecimalMax(value = "100.0", message = "Maximo 100%")
    BigDecimal humedadPorcentaje,

    @DecimalMin(value = "0.0", message = "Minimo 0%")
    @DecimalMax(value = "100.0", message = "Maximo 100%")
    BigDecimal fermentacionPorcentaje,

    Long fincaId
) {
    public Lote toCommand() {
        Lote lote = new Lote();
        lote.setPesoKg(this.pesoKg);
        lote.setHumedadPorcentaje(this.humedadPorcentaje);
        lote.setFermentacionPorcentaje(this.fermentacionPorcentaje);
        if (this.fincaId != null) {
            Finca finca = new Finca();
            finca.setId(this.fincaId);
            lote.setFinca(finca);
        }
        return lote;
    }
}
