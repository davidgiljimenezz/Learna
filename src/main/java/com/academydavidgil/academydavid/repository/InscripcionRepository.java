package com.academydavidgil.academydavid.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.academydavidgil.academydavid.model.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    boolean existsByUsuarioIdAndCursoId(Long usuarioId, Long cursoId);
    @EntityGraph(attributePaths = "curso")
    List<Inscripcion> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
}
