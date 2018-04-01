package br.com.musicasparamissa.api.cc.service;

import br.com.musicasparamissa.api.cc.entity.Artista;
import br.com.musicasparamissa.api.cc.entity.Musica;
import br.com.musicasparamissa.api.cc.repository.ArtistaRepository;
import br.com.musicasparamissa.api.cc.repository.MusicaRepository;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.hubspot.jinjava.Jinjava;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

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
    @Autowired()
    private SiteStorage siteStorage;

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

        Map<String, Object> context = Maps.newHashMap();
        context.put("nome", artista.getNome());
        context.put("slug", artista.getSlug());

        String content = renderTemplate("cifrascatolicas/templates/artista.html", context);

        siteStorage.saveFile(String.format("/%s/index.html", artista.getSlug()), content);

    }

    private void generateOnlyMusica(Musica musica) {

        Map<String, Object> context = Maps.newHashMap();
        context.put("nome", musica.getNome());
        context.put("slug", musica.getSlug());

        String content = renderTemplate("cifrascatolicas/templates/musica.html", context);

        siteStorage.saveFile(String.format("/%s/%s/index.html", musica.getArtista().getSlug(), musica.getSlug()), content);

    }

    private String renderTemplate(String templateName, Map<String, Object> context) {
        Jinjava jinjava = new Jinjava();

        String template = null;
        try {
            template = Resources.toString(Resources.getResource(templateName), Charsets.UTF_8);
        } catch (IOException e) {
            log.error("Error on getting template page.");
        }

        return jinjava.render(template, context);
    }

}
