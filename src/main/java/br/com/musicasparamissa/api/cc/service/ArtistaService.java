package br.com.musicasparamissa.api.cc.service;

import br.com.musicasparamissa.api.cc.entity.Artista;
import br.com.musicasparamissa.api.cc.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service("CcArtistaService")
public class ArtistaService {
	
	@Autowired
	private ArtistaRepository artistaRepository;

    public boolean exists(String slug) {
        return artistaRepository.exists(slug);
    }

	public Page<Artista> search(String filter, Pageable pageable) {
		return artistaRepository.findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContaining(filter, filter, pageable);
	}

    @Transactional
	public void save(Artista artista) {

        if(artista.getDataCadastro() == null)
            artista.setDataCadastro(LocalDate.now());
            artista.setDataUltimaEdicao(LocalDate.now());

        artistaRepository.save(artista);
	}

    @Transactional
    public void delete(Artista artista) {
        artistaRepository.delete(artista);
    }

    public Artista getArtista(String slug) {
        return artistaRepository.findOne(slug);
    }

    public List<Artista> list() {
        return artistaRepository.findAllByOrderByNome();
    }
}
