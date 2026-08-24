package ec.edu.uteq.agrotrace.lote.web;

import ec.edu.uteq.agrotrace.lote.domain.EstadoLote;
import ec.edu.uteq.agrotrace.lote.domain.Lote;
import ec.edu.uteq.agrotrace.lote.service.LoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lotes")
public class LoteWebController {

    @Autowired
    private LoteService loteService;

    // TODO-GA-03: Listar lotes con filtro por estado (delega, no decide)
    @GetMapping
    public String listar(
            @RequestParam(required = false) EstadoLote estado,
            Model model) {

        model.addAttribute("lotes", loteService.buscar(estado));
        model.addAttribute("estados", EstadoLote.values());
        model.addAttribute("estadoSeleccionado", estado);
        return "lotes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("lote", new Lote());
        return "lotes/formulario";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Lote lote = loteService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));
        model.addAttribute("lote", lote);
        return "lotes/detalle";
    }

    // TODO-GA-03: Registrar lote nuevo (delega, no decide)
    @PostMapping
    public String registrar(
            @Valid @ModelAttribute Lote lote,
            BindingResult errores,
            RedirectAttributes flash) {

        if (errores.hasErrors()) {
            return "lotes/formulario";
        }
        try {
            Lote creado = loteService.registrar(lote);
            flash.addFlashAttribute("mensaje",
                    "Lote " + creado.getCodigo() + " registrado como " + creado.getEstado());
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "lotes/formulario";
        }
        return "redirect:/lotes";
    }

    @GetMapping("/{id}/editar")
    public String editarLote(@PathVariable Long id, Model model) {
        Lote lote = loteService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));
        model.addAttribute("lote", lote);
        return "lotes/formulario";
    }

    @PostMapping("/{id}/actualizar")
    public String actualizarLote(@PathVariable Long id,
                                 @Valid @ModelAttribute Lote lote,
                                 BindingResult errores,
                                 RedirectAttributes flash) {
        if (errores.hasErrors()) {
            return "lotes/formulario";
        }
        try {
            loteService.actualizar(id, lote);
            flash.addFlashAttribute("mensaje", "Lote actualizado exitosamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "lotes/formulario";
        }
        return "redirect:/lotes";
    }

    @PostMapping("/{id}/anular")
    public String anularLote(@PathVariable Long id, RedirectAttributes flash) {
        try {
            loteService.anular(id);
            flash.addFlashAttribute("mensaje", "Lote anulado exitosamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/lotes";
    }
}
