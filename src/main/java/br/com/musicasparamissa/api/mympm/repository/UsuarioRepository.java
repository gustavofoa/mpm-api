package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mympm.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MyMpMUsuarioRepository")
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    Page<Usuario> findByEmailIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByDataCompraDesc(String email, String nome, Pageable pageable);

    List<Usuario> findAllByOrderByNome();
}
