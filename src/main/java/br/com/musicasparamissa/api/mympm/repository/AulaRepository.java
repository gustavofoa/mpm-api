package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mympm.entity.Aula;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends CrudRepository<Aula, Long> {

    @Query(value = "select a.id, a.numero, a.titulo, a.descricao, a.video, a.id_curso from mympm_aula_curso a" +
            " inner join mympm_usuario_aulacurso ua on ua.id_aulacurso = a.id" +
            " where ua.id_usuario = ? and ua.concluida = 1", nativeQuery = true)
    List<Aula> getAulaByUsuario(@Param("id_usuario") Long idUsuario);

}
