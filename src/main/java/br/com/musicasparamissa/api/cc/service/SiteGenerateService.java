package br.com.musicasparamissa.api.cc.service;

import br.com.musicasparamissa.api.cc.entity.Artista;
import br.com.musicasparamissa.api.cc.entity.Musica;
import br.com.musicasparamissa.api.cc.repository.ArtistaRepository;
import br.com.musicasparamissa.api.cc.repository.MusicaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CcSiteGenerateService")
public class SiteGenerateService {

    /*
     * Planejamento:
     *
     * - Gerar tudo
     * - Gerar home
     * - Gerar página do artista
     * - Gerar página da música
     *
     */

    @Autowired
    private ArtistaRepository artistaRepository;
    @Autowired
    private MusicaRepository musicaRepository;

	public void generateAll(){

	    generateHome();
	    for(Artista artista : artistaRepository.findAll())
	        generateArtista(artista.getSlug());

    }

    public void generateHome(){
        log.info("[CC] Generating Home.");
        //Pensar na home depois
        //Fazer interface de gerenciamento
    }

    public void generateArtista(String slugArtista){
        log.info("[CC] Generating Artista page: " + slugArtista);

	    Artista artista = artistaRepository.findOne(slugArtista);

	    generateOnlyArtista(artista);

        for(Musica musica : musicaRepository.findByArtistaSlug(slugArtista))
            generateOnlyMusica(musica);

    }

    public void generateMusica(String slugMusica){

        log.info("[CC] Generating Musica page: " + slugMusica);

        Musica musica = musicaRepository.findOne(slugMusica);

        generateOnlyMusica(musica);

        generateOnlyArtista(musica.getArtista());

    }

    private void generateOnlyArtista(Artista artista) {
        //TODO implement artista page generation
    }

    private void generateOnlyMusica(Musica musica) {
        //TODO implement musica page generation
    }

}
