package com.academydavidgil.academydavid.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.academydavidgil.academydavid.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByPublicadoTrueOrderByTituloAsc();
    Optional<Curso> findByIdAndPublicadoTrue(Long id);
}
