package br.com.musicasparamissa.api.mpm.repository;

import br.com.musicasparamissa.api.mpm.entity.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, String> {

    Set<Categoria> findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContainingOrDescricaoIgnoreCaseContaining(String slug, String nome, String descricao);

    Set<Categoria> findByCategoriaMaeOrderByOrdem(String slug);

    Set<Categoria> findByCategoriaMaeAndSlugLikeOrderByOrdem(Categoria categoriaMae, String slug);
}
