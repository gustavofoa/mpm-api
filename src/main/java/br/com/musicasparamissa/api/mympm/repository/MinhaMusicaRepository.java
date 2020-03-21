package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mympm.entity.MinhaMusica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MyMpMMinhaMusicaRepository")
public interface MinhaMusicaRepository extends CrudRepository<MinhaMusica, Long> {

    List<MinhaMusica> findByUsuarioId(Long idUsuario);

}
