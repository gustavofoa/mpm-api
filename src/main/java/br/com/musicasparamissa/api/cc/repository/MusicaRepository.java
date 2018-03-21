package br.com.musicasparamissa.api.cc.repository;

import br.com.musicasparamissa.api.cc.entity.Musica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CcMusicaRepository")
public interface MusicaRepository extends CrudRepository<Musica, String> {

    Page<Musica> findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContaining(String slug, String nome, Pageable pageable);

    List<Musica> findByArtistaSlug(String slug);

}
