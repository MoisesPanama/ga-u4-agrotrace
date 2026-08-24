package ec.edu.uteq.agrotrace.lote.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

    // TODO-GA-02: Consultas derivadas sin concatenar (OWASP A03)
    Optional<Lote> findByCodigo(String codigo);

    List<Lote> findByEstado(EstadoLote estado);

    List<Lote> findByFincaIdAndEstadoOrderByFechaRecepcionDesc(Long fincaId, EstadoLote estado);

    long countByEstado(EstadoLote estado);

    List<Lote> findByFincaId(Long fincaId);

    List<Lote> findByCertificado(Boolean certificado);

    @Query("SELECT l FROM Lote l WHERE l.humedadPorcentaje BETWEEN :min AND :max")
    List<Lote> findByHumedadRango(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Query("SELECT l FROM Lote l WHERE l.pesoKg >= :pesoMinimo")
    List<Lote> findByPesoMinimo(@Param("pesoMinimo") BigDecimal pesoMinimo);

    @Query("SELECT SUM(l.pesoKg) FROM Lote l WHERE l.estado = 'ACEPTADO'")
    BigDecimal sumPesoAceptados();

    @Query("SELECT l FROM Lote l WHERE l.fechaRecepcion >= :fecha")
    List<Lote> findRecientes(@Param("fecha") java.time.LocalDate fecha);

    List<Lote> findByFincaProductorNombreContainingIgnoreCase(String nombre);
}
