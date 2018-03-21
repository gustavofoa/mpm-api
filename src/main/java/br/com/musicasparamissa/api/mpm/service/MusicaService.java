package br.com.musicasparamissa.api.mpm.service;

import br.com.musicasparamissa.api.mpm.entity.Musica;
import br.com.musicasparamissa.api.mpm.entity.SugestaoMusica;
import br.com.musicasparamissa.api.mpm.repository.ItemLiturgiaRepository;
import br.com.musicasparamissa.api.mpm.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;

@Service
public class MusicaService {
	
	@Autowired
	private MusicaRepository musicaRepository;

	@Autowired
	private ItemLiturgiaRepository itemLiturgiaRepository;

    public boolean exists(String slug) {
        return musicaRepository.exists(slug);
    }

	public Page<Musica> search(String filter, Pageable pageable) {
		return musicaRepository.findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContainingOrLetraIgnoreCaseContaining(filter, filter, filter, pageable);
	}

    @Transactional
	public void save(Musica musica) {

        if(musica.getDataCadastro() == null)
            musica.setDataCadastro(LocalDate.now());
        musica.setDataUltimaEdicao(LocalDate.now());

        musicaRepository.save(musica);
	}

    @Transactional
    public void delete(Musica musica) {
        Set<SugestaoMusica> sugestoesMusica = itemLiturgiaRepository.findByMusica(musica);
        for (SugestaoMusica sugestaoMusica : sugestoesMusica) {
            sugestaoMusica.getAvulsas().remove(musica);
            sugestaoMusica.getRemover().remove(musica);
            itemLiturgiaRepository.save(sugestaoMusica);
        }
        musicaRepository.delete(musica);
    }

    public Musica getMusica(String slug) {
        return musicaRepository.findOne(slug);
    }
}
