package br.com.musicasparamissa.mpmjadmin.backend.repository;

import br.com.musicasparamissa.mpmjadmin.backend.entity.DiaLiturgico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DiaLiturgicoRepository extends CrudRepository<DiaLiturgico, String> {

    Page<DiaLiturgico> findBySlugIgnoreCaseContainingOrTituloIgnoreCaseContaining(String slug, String titulo, Pageable pageable);

}
