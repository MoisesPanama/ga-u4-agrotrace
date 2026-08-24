package ec.edu.uteq.agrotrace.clima;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clima")
public class ClimaRestController {

    private final ClimaService climaService;

    public ClimaRestController(ClimaService climaService) {
        this.climaService = climaService;
    }

    @GetMapping("/secado")
    public ResponseEntity<Map<String, Object>> pronosticoSecado() {
        PronosticoSecado pronostico = climaService.consultarTolerante();
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("disponible", pronostico.disponible());
        respuesta.put("horas", pronostico.horas());
        respuesta.put("temperaturas", pronostico.temperaturas());
        respuesta.put("humedades", pronostico.humedades());
        respuesta.put("precipitaciones", pronostico.precipitaciones());
        return ResponseEntity.ok(respuesta);
    }
}
