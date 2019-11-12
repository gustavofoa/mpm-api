package br.com.musicasparamissa.api.mympm.service;

import br.com.musicasparamissa.api.mympm.entity.DuvidaSugestao;
import br.com.musicasparamissa.api.mympm.entity.Notificacao;
import br.com.musicasparamissa.api.mympm.repository.DuvidaSugestaoRepository;
import br.com.musicasparamissa.api.mympm.repository.NotificacaoRepository;
import br.com.musicasparamissa.api.mympm.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("MyMpMDuvidaSugestaoService")
public class DuvidaSugestaoService {

	@Autowired
	private DuvidaSugestaoRepository duvidaSugestaoRepository;

	@Autowired
	private NotificacaoRepository notificacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailService emailService;

	public Page<DuvidaSugestao> search(String filter, Pageable pageable) {
		return duvidaSugestaoRepository.findByTituloIgnoreCaseContainingOrTextoIgnoreCaseContainingOrderByDataDesc(filter, filter, pageable);
	}

	public void answer(DuvidaSugestao duvidaSugestao) {

		DuvidaSugestao dbDuvidaSugestao = duvidaSugestaoRepository.findOne(duvidaSugestao.getId());
		dbDuvidaSugestao.setResposta(duvidaSugestao.getResposta());
		dbDuvidaSugestao.setStatus("respondida");
		duvidaSugestaoRepository.save(dbDuvidaSugestao);

		//Notificação
		Notificacao notificacao = new Notificacao();
		notificacao.setDestinatario(dbDuvidaSugestao.getUsuario());
		notificacao.setRemetente(usuarioRepository.findOne(1L));
		notificacao.setData(new Date());
		notificacao.setLink("/duvidas-e-sugestoes");
		String tituloNotificacao = dbDuvidaSugestao.getTipo().equals("duvida") ? "Dúvida" : "Sugestão";
		notificacao.setTitulo(tituloNotificacao + " respondida!");
		notificacao.setTexto("Acabamos de responder a sua "+ tituloNotificacao + ".");
		notificacaoRepository.save(notificacao);

		//E-mail
		emailService.send(dbDuvidaSugestao.getUsuario().getEmail(),
				"Respondemos a sua "+tituloNotificacao,
				"<p>Olá " + dbDuvidaSugestao.getUsuario().getNome() + ", estamos passando só pra avisar que respondemos a " + tituloNotificacao +
						" que você nos enviou pela plataforma.</p> <p>Para visualizar a nossa resposta acesse a plataforma em " +
						"<a href='http://minhas.musicasparamissa.com.br/duvidas-e-sugestoes'>http://minhas.musicasparamissa.com.br/duvidas-e-sugestoes</a></p>" +
						"<p>Att,</p> <p>Equipe Músicas para Missa</p>"
		);

	}
}
