package br.com.musicasparamissa.api.cc.service;

import br.com.musicasparamissa.api.cc.entity.Artista;
import br.com.musicasparamissa.api.cc.entity.Musica;
import br.com.musicasparamissa.api.cc.repository.ArtistaRepository;
import br.com.musicasparamissa.api.cc.repository.MusicaRepository;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.loader.ResourceLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Slf4j
@Service("CcSiteGenerateService")
public class SiteGenerateService {

    private static final String TEMPLATE_PATH = "static.cifrascatolicas.com.br/app/templates/";

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

        generateSitemap();

        for(Artista artista : artistaRepository.findAll())
	        generateArtista(artista.getSlug());

        for(Musica musica : musicaRepository.findAll())
            generateOnlyMusica(musica);

    }

    private void generateSitemap() {

	    String sitemap = "sitemap";

        siteStorage.saveFile("sitemap.xml", sitemap);
    }

    public void generateHome(){
        log.info("[CC] Generating Home.");

        //Pensar na home depois
        //Fazer interface de gerenciamento

        Map<String, Object> context = getContext();

        context.put("artistas", artistaRepository.findAllByOrderByNome());

        String content = renderTemplate(TEMPLATE_PATH + "index.html", context);

        siteStorage.saveFile("index.html", content);
    }

    public void generateArtista(String slugArtista){
        log.info("[CC] Generating Artista page: " + slugArtista);

	    Artista artista = artistaRepository.findOne(slugArtista);
        List<Musica> musicas = musicaRepository.findByArtistaSlug(slugArtista);

	    generateOnlyArtista(artista, musicas);

    }

    public void generateMusica(String slugMusica){

        log.info("[CC] Generating Musica page: " + slugMusica);

        Musica musica = musicaRepository.findOne(slugMusica);

        generateOnlyMusica(musica);

        generateOnlyArtista(musica.getArtista(), musicaRepository.findByArtistaSlug(musica.getArtista().getSlug()));

    }

    private void generateOnlyArtista(Artista artista, List<Musica> musicas) {

        Map<String, Object> context = getContext();

        Map<String, Object> artistaContext = Maps.newHashMap();
        context.put("artista", artistaContext);

        artistaContext.put("nome", artista.getNome());
        artistaContext.put("slug", artista.getSlug());
        artistaContext.put("img", artista.getImagem());
        artistaContext.put("info", artista.getInfo());

        artistaContext.put("musicas", musicas);

        String content = renderTemplate(TEMPLATE_PATH + "artista.html", context);

        siteStorage.saveFile(String.format("%s/index.html", artista.getSlug()), content);

    }

    private void generateOnlyMusica(Musica musica) {

        Map<String, Object> context = getContext();

        Map<String, Object> musicaContext = Maps.newHashMap();
        context.put("musica", musicaContext);

        musicaContext.put("slug", musica.getSlug());
        musicaContext.put("titulo", musica.getNome());
        musicaContext.put("cifra", musica.getCifra());

        Map<String, Object> artistaContext = Maps.newHashMap();
        musicaContext.put("artista", artistaContext);

        artistaContext.put("slug", musica.getArtista().getSlug());
        artistaContext.put("nome", musica.getArtista().getNome());
        artistaContext.put("img", musica.getArtista().getImagem());


        String content = renderTemplate(TEMPLATE_PATH + "musica.html", context);

        siteStorage.saveFile(String.format("%s/%s/index.html", musica.getArtista().getSlug(), musica.getSlug()), content);

    }

    private Map<String, Object> getContext() {
        Map<String, Object> context = Maps.newHashMap();
        context.put("STATICPATH", "https://static.cifrascatolicas.com.br");
        return context;
    }

    private String renderTemplate(String templateName, Map<String, Object> context) {
        Jinjava jinjava = new Jinjava();

        jinjava.setResourceLocator(new ResourceLocator() {
            @Override
            public String getString(String s, Charset charset, JinjavaInterpreter jinjavaInterpreter) throws IOException {

                String filePath = TEMPLATE_PATH+s;

                return getFile(filePath);

            }
        });

        String template = null;
        try {
            template = Resources.toString(Resources.getResource(templateName), Charsets.UTF_8);
        } catch (IOException e) {
            log.error("Error on getting template page.");
        }

        return jinjava.render(template, context);
    }

    private String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

}
