package com.uteq.agrotrace.service;

import com.uteq.agrotrace.model.Lote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CertificacionService {

    @Autowired
    private LoteService loteService;

    // TODO-GA-10: Consumir servicio SOAP de certificacion
    // TODO-GA-12: Consumo de servicios desde servidor con manejo de errores
    public Map<String, Object> certificarLote(Long loteId) {
        Lote lote = loteService.buscarPorId(loteId)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("loteId", lote.getId());
        resultado.put("codigo", lote.getCodigo());

        boolean cumpleRequisitos = validarRequisitosCertificacion(lote);
        resultado.put("cumpleRequisitos", cumpleRequisitos);

        if (cumpleRequisitos) {
            lote.setCertificado(true);
            lote.setEstado("CERTIFICADO");
            loteService.guardar(lote);
            resultado.put("estado", "CERTIFICADO");
            resultado.put("mensaje", "Lote certificado exitosamente");
        } else {
            resultado.put("estado", "RECHAZADO");
            resultado.put("mensaje", "El lote no cumple los requisitos de certificacion");
        }

        return resultado;
    }

    private boolean validarRequisitosCertificacion(Lote lote) {
        if (lote.getHumedadPorcentaje() == null || lote.getFermentacionHoras() == null) {
            return false;
        }

        boolean humedadOk = lote.getHumedadPorcentaje().compareTo(new BigDecimal("5.0")) >= 0
                && lote.getHumedadPorcentaje().compareTo(new BigDecimal("10.0")) <= 0;

        boolean fermentacionOk = lote.getFermentacionHoras() >= 120
                && lote.getFermentacionHoras() <= 336;

        boolean temperaturasOk = lote.getTemperaturaMaxima() != null
                && lote.getTemperaturaMinima() != null
                && lote.getTemperaturaMaxima().compareTo(new BigDecimal("40.0")) >= 0
                && lote.getTemperaturaMaxima().compareTo(new BigDecimal("50.0")) <= 0;

        return humedadOk && fermentacionOk && temperaturasOk;
    }
}
