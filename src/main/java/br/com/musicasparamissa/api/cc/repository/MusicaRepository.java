package br.com.musicasparamissa.api.cc.repository;

import br.com.musicasparamissa.api.cc.entity.Musica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CcMusicaRepository")
public interface MusicaRepository extends CrudRepository<Musica, String> {

    Page<Musica> findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContaining(String slug, String nome, Pageable pageable);

    List<Musica> findByArtistaSlug(String slug);

    @Query("select count(m.slug)>0 from cc_musica m where m.artista.slug = :artista and m.slug = :slug")
    boolean exists(String artista, String slug);

    @Query("select m from cc_musica m where m.artista.slug = :artista and m.slug = :slug")
    Musica findOne(String artista, String slug);

}
