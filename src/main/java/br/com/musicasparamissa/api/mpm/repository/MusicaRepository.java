package br.com.musicasparamissa.api.mpm.repository;

import br.com.musicasparamissa.api.mpm.entity.Categoria;
import br.com.musicasparamissa.api.mpm.entity.Musica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MusicaRepository extends CrudRepository<Musica, String> {

    @Query("select m from mpm_musica m inner join m.categorias c where c = :categoria")
    List<Musica> findByCategoria(@Param("categoria") Categoria categoria);

    Set<Musica> findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContainingOrLetraIgnoreCaseContaining(String slug,
                                                                                                    String nome, String letra);

    Page<Musica> findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContainingOrLetraIgnoreCaseContaining(String slug, String nome, String letra, Pageable pageable);

}
