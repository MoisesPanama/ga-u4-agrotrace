package com.uteq.agrotrace.service;

import com.uteq.agrotrace.model.Lote;
import com.uteq.agrotrace.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    // TODO-GA-04: Implementar servicio con logica de negocio
    public List<Lote> listarTodos() {
        return loteRepository.findAll();
    }

    public Optional<Lote> buscarPorId(Long id) {
        return loteRepository.findById(id);
    }

    public Optional<Lote> buscarPorCodigo(String codigo) {
        return loteRepository.findByCodigo(codigo);
    }

    public Lote guardar(Lote lote) {
        validarReglasNegocio(lote);
        return loteRepository.save(lote);
    }

    public Lote actualizar(Long id, Lote loteActualizado) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado con id: " + id));

        lote.setProductorNombre(loteActualizado.getProductorNombre());
        lote.setProductorCedula(loteActualizado.getProductorCedula());
        lote.setFincaNombre(loteActualizado.getFincaNombre());
        lote.setFincaUbicacion(loteActualizado.getFincaUbicacion());
        lote.setVariedad(loteActualizado.getVariedad());
        lote.setHectareas(loteActualizado.getHectareas());
        lote.setFechaCosecha(loteActualizado.getFechaCosecha());
        lote.setFechaProcesamiento(loteActualizado.getFechaProcesamiento());
        lote.setPesoKg(loteActualizado.getPesoKg());
        lote.setHumedadPorcentaje(loteActualizado.getHumedadPorcentaje());
        lote.setFermentacionHoras(loteActualizado.getFermentacionHoras());
        lote.setTemperaturaMaxima(loteActualizado.getTemperaturaMaxima());
        lote.setTemperaturaMinima(loteActualizado.getTemperaturaMinima());
        lote.setEstado(loteActualizado.getEstado());
        lote.setObservaciones(loteActualizado.getObservaciones());

        validarReglasNegocio(lote);
        return loteRepository.save(lote);
    }

    public void eliminar(Long id) {
        if (!loteRepository.existsById(id)) {
            throw new RuntimeException("Lote no encontrado con id: " + id);
        }
        loteRepository.deleteById(id);
    }

    public Lote anular(Long id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado con id: " + id));
        lote.setEstado("ANULADO");
        return loteRepository.save(lote);
    }

    public List<Lote> buscarPorEstado(String estado) {
        return loteRepository.findByEstado(estado);
    }

    public List<Lote> buscarPorProductor(String cedula) {
        return loteRepository.findByProductorCedula(cedula);
    }

    public List<Lote> buscarCertificados() {
        return loteRepository.findByCertificado(true);
    }

    public List<Lote> buscarPorVariedad(String variedad) {
        return loteRepository.findByVariedad(variedad);
    }

    public List<Lote> buscarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return loteRepository.findByFechaCosechaBetween(inicio, fin);
    }

    public List<Lote> buscarPorPesoMinimo(BigDecimal pesoMinimo) {
        return loteRepository.findByPesoMinimo(pesoMinimo);
    }

    public List<Lote> buscarPorHumedadRango(BigDecimal min, BigDecimal max) {
        return loteRepository.findByHumedadRango(min, max);
    }

    public Long contarPorEstado(String estado) {
        return loteRepository.countByEstado(estado);
    }

    public BigDecimal sumarPesoCertificados() {
        BigDecimal suma = loteRepository.sumPesoCertificados();
        return suma != null ? suma : BigDecimal.ZERO;
    }

    // TODO-GA-02: Validacion de reglas de negocio para humedad y fermentacion
    private void validarReglasNegocio(Lote lote) {
        if (lote.getHumedadPorcentaje() != null) {
            if (lote.getHumedadPorcentaje().compareTo(new BigDecimal("5.0")) < 0 ||
                lote.getHumedadPorcentaje().compareTo(new BigDecimal("12.0")) > 0) {
                throw new RuntimeException(
                    "La humedad debe estar entre 5% y 12%. Valor actual: " + lote.getHumedadPorcentaje() + "%");
            }
        }

        if (lote.getFermentacionHoras() != null) {
            if (lote.getFermentacionHoras() < 100 || lote.getFermentacionHoras() > 336) {
                throw new RuntimeException(
                    "La fermentacion debe ser entre 100 y 336 horas. Valor actual: " + lote.getFermentacionHoras() + "h");
            }
        }

        if (lote.getTemperaturaMaxima() != null && lote.getTemperaturaMinima() != null) {
            if (lote.getTemperaturaMaxima().compareTo(lote.getTemperaturaMinima()) <= 0) {
                throw new RuntimeException(
                    "La temperatura maxima debe ser mayor que la minima");
            }
        }
    }
}
