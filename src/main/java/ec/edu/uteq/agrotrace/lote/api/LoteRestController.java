package ec.edu.uteq.agrotrace.lote.api;

import ec.edu.uteq.agrotrace.common.exception.LoteNoEncontradoException;
import ec.edu.uteq.agrotrace.lote.api.dto.CrearLoteRequest;
import ec.edu.uteq.agrotrace.lote.api.dto.LoteResponse;
import ec.edu.uteq.agrotrace.lote.domain.EstadoLote;
import ec.edu.uteq.agrotrace.lote.domain.Lote;
import ec.edu.uteq.agrotrace.lote.service.LoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lotes")
@Tag(name = "Lotes", description = "API de gestion de lotes de cacao para exportadoras")
public class LoteRestController {

    @Autowired
    private LoteService loteService;

    // TODO-GA-05: GET /api/v1/lotes con paginacion y filtro opcional
    // TODO-GA-08: Documentacion OpenAPI con anotaciones
    @Operation(
        summary = "Lista los lotes de cacao del centro de acopio",
        description = "Devuelve una pagina de lotes, filtrable por estado y por finca.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pagina de lotes"),
        @ApiResponse(responseCode = "401", description = "Token ausente o invalido"),
        @ApiResponse(responseCode = "422", description = "Parametro de filtro invalido")
    })
    @GetMapping
    public ResponseEntity<List<LoteResponse>> listar(
            @RequestParam(required = false) EstadoLote estado,
            @RequestParam(required = false) Long fincaId) {

        List<Lote> lotes = loteService.buscar(estado);
        List<LoteResponse> respuesta = lotes.stream()
                .map(LoteResponse::desde)
                .toList();
        return ResponseEntity.ok(respuesta);
    }

    // TODO-GA-05: GET /api/v1/lotes/{codigo}
    @Operation(summary = "Obtiene un lote por su codigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<LoteResponse> obtener(@PathVariable String codigo) {
        Lote lote = loteService.buscarPorCodigo(codigo)
                .orElseThrow(() -> new LoteNoEncontradoException(codigo));
        return ResponseEntity.ok(LoteResponse.desde(lote));
    }

    // TODO-GA-06: POST con 201 Created + Location
    @Operation(summary = "Registra un lote nuevo")
    @PostMapping
    public ResponseEntity<LoteResponse> crear(
            @Valid @RequestBody CrearLoteRequest peticion,
            UriComponentsBuilder uriBuilder) {

        Lote creado = loteService.registrar(peticion.toCommand());

        URI ubicacion = uriBuilder
                .path("/api/v1/lotes/{codigo}")
                .buildAndExpand(creado.getCodigo())
                .toUri();

        return ResponseEntity.created(ubicacion)
                .body(LoteResponse.desde(creado));
    }

    // PUT /api/v1/lotes/{codigo}
    @Operation(summary = "Actualiza un lote existente")
    @PutMapping("/{codigo}")
    public ResponseEntity<LoteResponse> actualizar(
            @PathVariable String codigo,
            @Valid @RequestBody CrearLoteRequest peticion) {

        Lote lote = loteService.buscarPorCodigo(codigo)
                .orElseThrow(() -> new LoteNoEncontradoException(codigo));

        lote.setPesoKg(peticion.pesoKg());
        lote.setHumedadPorcentaje(peticion.humedadPorcentaje());
        lote.setFermentacionPorcentaje(peticion.fermentacionPorcentaje());

        Lote actualizado = loteService.actualizar(lote.getId(), lote);
        return ResponseEntity.ok(LoteResponse.desde(actualizado));
    }

    // DELETE /api/v1/lotes/{codigo}
    @Operation(summary = "Elimina un lote")
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigo) {
        Lote lote = loteService.buscarPorCodigo(codigo)
                .orElseThrow(() -> new LoteNoEncontradoException(codigo));
        loteService.eliminar(lote.getId());
        return ResponseEntity.noContent().build();
    }

    // GET /api/v1/lotes/resumen
    @Operation(summary = "Resumen general de lotes")
    @GetMapping("/resumen")
    public ResponseEntity<java.util.Map<String, Object>> resumen() {
        java.util.Map<String, Object> resumen = new java.util.HashMap<>();
        resumen.put("totalLotes", loteService.listarTodos().size());
        resumen.put("aceptados", loteService.contarPorEstado(EstadoLote.ACEPTADO));
        resumen.put("rechazados", loteService.contarPorEstado(EstadoLote.RECHAZADO));
        resumen.put("secadoAdicional", loteService.contarPorEstado(EstadoLote.SECADO_ADICIONAL));
        return ResponseEntity.ok(resumen);
    }
}
