package br.com.musicasparamissa.api.cc.service;

import br.com.musicasparamissa.api.cc.entity.Musica;
import br.com.musicasparamissa.api.cc.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service("CcMusicaService")
public class MusicaService {
	
	@Autowired
	private MusicaRepository musicaRepository;

    public boolean exists(String artista, String slug) {
        return musicaRepository.exists(artista, slug);
    }

	public Page<Musica> search(String filter, Pageable pageable) {
		return musicaRepository.findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContaining(filter, filter, pageable);
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
        musicaRepository.delete(musica);
    }

    public Musica getMusica(String artista, String slug) {
        return musicaRepository.findOne(artista, slug);
    }

    public List<Musica> findByArtista(String slug) {
        return musicaRepository.findByArtistaSlug(slug);
    }
}
