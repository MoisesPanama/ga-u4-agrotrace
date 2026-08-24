package com.uteq.agrotrace.rest;

import com.uteq.agrotrace.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/clima")
public class ClimaRestController {

    @Autowired
    private ClimaService climaService;

    @GetMapping("/{ciudad}")
    public ResponseEntity<Map<String, Object>> obtenerClima(@PathVariable String ciudad) {
        Map<String, Object> clima = climaService.obtenerClima(ciudad);
        return ResponseEntity.ok(clima);
    }

    @DeleteMapping("/{ciudad}/cache")
    public ResponseEntity<Void> limpiarCache(@PathVariable String ciudad) {
        climaService.limpiarCache(ciudad);
        return ResponseEntity.noContent().build();
    }
}
