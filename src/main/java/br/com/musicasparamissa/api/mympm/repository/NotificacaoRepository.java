package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mympm.entity.Notificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("MyMpMNotificacaoRepository")
public interface NotificacaoRepository extends CrudRepository<Notificacao, Integer> {

    Page<Notificacao> findByTituloIgnoreCaseContainingOrTextoIgnoreCaseContainingOrDestinatarioNomeIgnoreCaseContainingOrderByDataDesc(String titulo, String texto, String destinatario, Pageable pageable);

}
