package ec.edu.uteq.agrotrace.lote.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FincaRepository extends JpaRepository<Finca, Long> {

    Optional<Finca> findByNombre(String nombre);

    List<Finca> findByProductorCedula(String cedula);

    List<Finca> findByProductorNombreContainingIgnoreCase(String nombre);
}
