package br.com.musicasparamissa.api.cc.repository;

import br.com.musicasparamissa.api.cc.entity.Artista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CcArtistaRepository")
public interface ArtistaRepository extends CrudRepository<Artista, String> {

    Page<Artista> findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContaining(String slug, String nome, Pageable pageable);

    List<Artista> findAllByOrderByNome();
}
