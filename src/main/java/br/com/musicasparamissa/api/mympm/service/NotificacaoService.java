package br.com.musicasparamissa.api.mympm.service;

import br.com.musicasparamissa.api.mympm.entity.Notificacao;
import br.com.musicasparamissa.api.mympm.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("MyMpMNotificacaoService")
public class NotificacaoService {
	
	@Autowired
	private NotificacaoRepository notificacaoRepository;

	public Page<Notificacao> search(String filter, Pageable pageable) {
		return notificacaoRepository.findByTituloIgnoreCaseContainingOrTextoIgnoreCaseContainingOrDestinatarioNomeIgnoreCaseContainingOrderByDataDesc(filter, filter, filter, pageable);
	}

}
