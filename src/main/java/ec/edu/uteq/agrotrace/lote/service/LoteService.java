package ec.edu.uteq.agrotrace.lote.service;

import ec.edu.uteq.agrotrace.lote.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private FincaRepository fincaRepository;

    public List<Lote> listarTodos() {
        return loteRepository.findAll();
    }

    public Optional<Lote> buscarPorId(Long id) {
        return loteRepository.findById(id);
    }

    public Optional<Lote> buscarPorCodigo(String codigo) {
        return loteRepository.findByCodigo(codigo);
    }

    // TODO-GA-03: Buscar con filtro por estado
    public List<Lote> buscar(EstadoLote estado) {
        if (estado != null) {
            return loteRepository.findByEstado(estado);
        }
        return loteRepository.findAll();
    }

    public Lote registrar(Lote lote) {
        lote.setEstado(lote.evaluarEstado());
        return loteRepository.save(lote);
    }

    public Lote actualizar(Long id, Lote loteActualizado) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado con id: " + id));

        lote.setFechaRecepcion(loteActualizado.getFechaRecepcion());
        lote.setPesoKg(loteActualizado.getPesoKg());
        lote.setFermentacionPorcentaje(loteActualizado.getFermentacionPorcentaje());
        lote.setHumedadPorcentaje(loteActualizado.getHumedadPorcentaje());
        lote.setObservaciones(loteActualizado.getObservaciones());

        lote.setEstado(lote.evaluarEstado());
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
        lote.setEstado(EstadoLote.ANULADO);
        return loteRepository.save(lote);
    }

    public long contarPorEstado(EstadoLote estado) {
        return loteRepository.countByEstado(estado);
    }
}
