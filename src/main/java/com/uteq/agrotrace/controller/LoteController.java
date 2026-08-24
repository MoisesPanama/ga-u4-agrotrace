package com.uteq.agrotrace.controller;

import com.uteq.agrotrace.model.Lote;
import com.uteq.agrotrace.service.CertificacionService;
import com.uteq.agrotrace.service.ClimaService;
import com.uteq.agrotrace.service.LoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class LoteController {

    @Autowired
    private LoteService loteService;

    @Autowired
    private CertificacionService certificacionService;

    @Autowired
    private ClimaService climaService;

    // TODO-GA-05: Implementar controlador MVC que delega en servicio
    @GetMapping("/lotes")
    public String listarLotes(Model model) {
        model.addAttribute("lotes", loteService.listarTodos());
        return "lotes";
    }

    @GetMapping("/lotes/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("lote", new Lote());
        return "lote-form";
    }

    @GetMapping("/lotes/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Lote lote = loteService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));
        model.addAttribute("lote", lote);

        Map<String, Object> clima = climaService.obtenerClima("Quevedo");
        model.addAttribute("clima", clima);
        return "lote-detail";
    }

    @PostMapping("/lotes")
    public String guardarLote(@Valid @ModelAttribute Lote lote,
                              BindingResult result,
                              RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "lote-form";
        }
        try {
            loteService.guardar(lote);
            flash.addFlashAttribute("success", "Lote registrado exitosamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "lote-form";
        }
        return "redirect:/lotes";
    }

    @GetMapping("/lotes/{id}/editar")
    public String editarLote(@PathVariable Long id, Model model) {
        Lote lote = loteService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));
        model.addAttribute("lote", lote);
        return "lote-form";
    }

    @PostMapping("/lotes/{id}/actualizar")
    public String actualizarLote(@PathVariable Long id,
                                 @Valid @ModelAttribute Lote lote,
                                 BindingResult result,
                                 RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "lote-form";
        }
        try {
            loteService.actualizar(id, lote);
            flash.addFlashAttribute("success", "Lote actualizado exitosamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "lote-form";
        }
        return "redirect:/lotes";
    }

    @PostMapping("/lotes/{id}/anular")
    public String anularLote(@PathVariable Long id, RedirectAttributes flash) {
        try {
            loteService.anular(id);
            flash.addFlashAttribute("success", "Lote anulado exitosamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/lotes";
    }

    @PostMapping("/lotes/{id}/certificar")
    public String certificarLote(@PathVariable Long id, RedirectAttributes flash) {
        try {
            certificacionService.certificarLote(id);
            flash.addFlashAttribute("success", "Lote certificado exitosamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/lotes";
    }

    @GetMapping("/lotes/buscar")
    @ResponseBody
    public java.util.List<Lote> buscarLotes(@RequestParam String q) {
        return loteService.buscarPorProductor(q);
    }
}
