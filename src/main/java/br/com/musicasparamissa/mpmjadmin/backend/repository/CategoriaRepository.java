package br.com.musicasparamissa.mpmjadmin.backend.repository;

import br.com.musicasparamissa.mpmjadmin.backend.entity.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, String> {

	Set<Categoria> findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContainingOrDescricaoIgnoreCaseContaining(String slug, String nome, String descricao);

	Set<Categoria> findByCategoriaMae(String slug);

}
