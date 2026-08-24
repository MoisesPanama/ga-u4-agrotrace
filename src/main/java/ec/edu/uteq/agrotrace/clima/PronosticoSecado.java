package ec.edu.uteq.agrotrace.clima;

import java.math.BigDecimal;
import java.util.List;

// Pronostico de secado para el centro de acopio APROCAFA
public record PronosticoSecado(
    List<String> horas,
    List<BigDecimal> temperaturas,
    List<BigDecimal> humedades,
    List<BigDecimal> precipitaciones,
    boolean disponible
) {
    public static PronosticoSecado noDisponible() {
        return new PronosticoSecado(
            List.of(), List.of(), List.of(), List.of(), false
        );
    }
}
