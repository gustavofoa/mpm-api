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
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
		return musicaRepository.findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContainingOrLetraIgnoreCaseContainingOrInfoIgnoreCaseContaining(filter, filter, filter, filter, pageable);
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

    public List<Musica> listWithoutVideo() {
        Iterable<Musica> musicas = musicaRepository.findAll();
        List<Musica> musicasWithoutVideo = new ArrayList<>();
        musicas.forEach(musica -> {
            if(musica.getVideoCode() == null) {
                musicasWithoutVideo.add(musica);
                return;
            }
            try{
                URL url = new URL(String.format("http://www.youtube.com/oembed?url=http://www.youtube.com/watch?v=%s&format=json",musica.getVideoCode()));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                System.out.println(String.format("Verificando video %s de %s (%d)", musica.getVideoCode(), musica.getSlug(), con.getResponseCode()));
                if(con.getResponseCode() == 404)
                    musicasWithoutVideo.add(musica);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return musicasWithoutVideo;
    }
}
