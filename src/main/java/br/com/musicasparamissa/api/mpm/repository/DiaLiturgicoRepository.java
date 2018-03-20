package br.com.musicasparamissa.api.mpm.repository;

import br.com.musicasparamissa.api.mpm.entity.DiaLiturgico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaLiturgicoRepository extends CrudRepository<DiaLiturgico, String> {

    Page<DiaLiturgico> findBySlugIgnoreCaseContainingOrTituloIgnoreCaseContaining(String slug, String titulo, Pageable pageable);

}
