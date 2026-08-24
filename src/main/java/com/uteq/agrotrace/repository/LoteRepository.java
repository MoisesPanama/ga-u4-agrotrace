package com.uteq.agrotrace.repository;

import com.uteq.agrotrace.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

    // TODO-GA-03: Implementar consultas derivadas
    Optional<Lote> findByCodigo(String codigo);

    List<Lote> findByEstado(String estado);

    List<Lote> findByProductorCedula(String cedula);

    List<Lote> findByCertificado(Boolean certificado);

    List<Lote> findByVariedad(String variedad);

    List<Lote> findByFechaCosechaBetween(LocalDate inicio, LocalDate fin);

    @Query("SELECT l FROM Lote l WHERE l.pesoKg >= :pesoMinimo")
    List<Lote> findByPesoMinimo(@Param("pesoMinimo") BigDecimal pesoMinimo);

    @Query("SELECT l FROM Lote l WHERE l.humedadPorcentaje BETWEEN :humedadMin AND :humedadMax")
    List<Lote> findByHumedadRango(@Param("humedadMin") BigDecimal humedadMin,
                                   @Param("humedadMax") BigDecimal humedadMax);

    @Query("SELECT l FROM Lote l WHERE l.estado = :estado AND l.certificado = :certificado")
    List<Lote> findByEstadoYCertificado(@Param("estado") String estado,
                                         @Param("certificado") Boolean certificado);

    @Query("SELECT l FROM Lote l WHERE l.fechaCosecha >= :fecha")
    List<Lote> findRecientes(@Param("fecha") LocalDate fecha);

    @Query("SELECT COUNT(l) FROM Lote l WHERE l.estado = :estado")
    Long countByEstado(@Param("estado") String estado);

    @Query("SELECT SUM(l.pesoKg) FROM Lote l WHERE l.estado = 'CERTIFICADO'")
    BigDecimal sumPesoCertificados();

    List<Lote> findByProductorNombreContainingIgnoreCase(String nombre);
}
