package com.uteq.agrotrace.rest;

import com.uteq.agrotrace.model.Lote;
import com.uteq.agrotrace.service.LoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/lotes")
public class LoteRestController {

    @Autowired
    private LoteService loteService;

    // TODO-GA-06: Publicar API REST para exportadoras
    // TODO-GA-07: Implementar versioning de API
    @GetMapping
    public ResponseEntity<List<Lote>> listarTodos(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String variedad,
            @RequestParam(required = false) Boolean certificado) {

        List<Lote> lotes;
        if (estado != null) {
            lotes = loteService.buscarPorEstado(estado);
        } else if (variedad != null) {
            lotes = loteService.buscarPorVariedad(variedad);
        } else if (certificado != null) {
            lotes = loteService.buscarCertificados();
        } else {
            lotes = loteService.listarTodos();
        }
        return ResponseEntity.ok(lotes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lote> obtenerPorId(@PathVariable Long id) {
        return loteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Lote> obtenerPorCodigo(@PathVariable String codigo) {
        return loteService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/inventario")
    public ResponseEntity<Map<String, Object>> inventarioDisponible() {
        Map<String, Object> inventario = new HashMap<>();
        inventario.put("totalLotes", loteService.listarTodos().size());
        inventario.put("lotesCertificados", loteService.buscarCertificados().size());
        inventario.put("pesoTotalCertificado", loteService.sumarPesoCertificados());
        inventario.put("lotesPendientes", loteService.contarPorEstado("REGISTRADO"));
        inventario.put("lotesEnVerificacion", loteService.contarPorEstado("EN_VERIFICACION"));
        inventario.put("timestamp", java.time.Instant.now());
        return ResponseEntity.ok(inventario);
    }

    @GetMapping("/inventario/variedad")
    public ResponseEntity<Map<String, Object>> inventarioPorVariedad(
            @RequestParam String variedad) {
        List<Lote> lotes = loteService.buscarPorVariedad(variedad);
        Map<String, Object> inventario = new HashMap<>();
        inventario.put("variedad", variedad);
        inventario.put("cantidad", lotes.size());
        inventario.put("pesoTotal", lotes.stream()
                .map(Lote::getPesoKg)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return ResponseEntity.ok(inventario);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Lote lote) {
        try {
            Lote creado = loteService.guardar(lote);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(errorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody Lote lote) {
        try {
            Lote actualizado = loteService.actualizar(id, lote);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(errorResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<?> anular(@PathVariable Long id) {
        try {
            Lote anulado = loteService.anular(id);
            return ResponseEntity.ok(anulado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(errorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        loteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> resumen() {
        Map<String, Object> resumen = new HashMap<>();
        resumen.put("totalLotes", loteService.listarTodos().size());
        resumen.put("certificados", loteService.contarPorEstado("CERTIFICADO"));
        resumen.put("pendientes", loteService.contarPorEstado("REGISTRADO"));
        resumen.put("enVerificacion", loteService.contarPorEstado("EN_VERIFICACION"));
        resumen.put("pesoTotalCertificado", loteService.sumarPesoCertificados());
        return ResponseEntity.ok(resumen);
    }

    private Map<String, Object> errorResponse(String mensaje) {
        Map<String, Object> error = new HashMap<>();
        error.put("type", "error");
        error.put("title", "Validacion fallida");
        error.put("status", 400);
        error.put("detail", mensaje);
        error.put("instance", "/api/v1/lotes");
        return error;
    }
}
