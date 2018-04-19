package br.com.musicasparamissa.api.mpm.service;

import br.com.musicasparamissa.api.mpm.entity.Musica;
import br.com.musicasparamissa.api.mpm.repository.CategoriaRepository;
import br.com.musicasparamissa.api.mpm.repository.DiaLiturgicoRepository;
import br.com.musicasparamissa.api.mpm.repository.MusicaRepository;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.loader.ResourceLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;

@Slf4j
@Service("MpmSiteGenerateService")
public class SiteGenerateService {

    private static final String TEMPLATE_PATH = "static.musicasparamissa.com.br/app/templates/";

    @Autowired
    private MusicaRepository musicaRepository;
    @Autowired
    private DiaLiturgicoRepository diaLiturgicoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired()
    private SiteStorage siteStorage;

	public void generateAll(){

	    generateHome();

        generateSitemap();

//        for(Musica musica : musicaRepository.findAll())
//            generateOnlyMusica(musica);

    }

    private void generateSitemap() {
        log.info("[MpM] Generating Sitemap.");

        Map<String, Object> context = getContext();

        context.put("musicas", musicaRepository.findAll());

        context.put("diasLiturgicos", diaLiturgicoRepository.findAll());

        context.put("categorias", categoriaRepository.findAll());

        String content = renderTemplate(TEMPLATE_PATH + "sitemap.xml", context);

        System.out.println("Sitemap content:" + content);

        siteStorage.saveFile("sitemap.xml", content, "text/xml");
    }

    public void generateHome(){
        log.info("[MpM] Generating Home.");

        Map<String, Object> context = getContext();

        String content = renderTemplate(TEMPLATE_PATH + "index.html", context);

        System.out.println("Home content:" + content);

        siteStorage.saveFile("index.html", content, "text/html");
    }

    public void generateMusica(String slugMusica){

        log.info("[MpM] Generating Musica page: " + slugMusica);

        Musica musica = musicaRepository.findOne(slugMusica);

        generateOnlyMusica(musica);

    }

    public void generateSugestoesPara(String diaLiturgico) {
    }

    public void generateMusicasDe(String categoria) {
    }

    private void generateOnlyMusica(Musica musica) {

        Map<String, Object> context = getContext();

        Map<String, Object> musicaContext = Maps.newHashMap();
        context.put("musica", musicaContext);

        musicaContext.put("slug", musica.getSlug());
        musicaContext.put("titulo", musica.getNome());
        musicaContext.put("cifra", musica.getCifra());


        String content = renderTemplate(TEMPLATE_PATH + "musica.html", context);

        siteStorage.saveFile(String.format("musica/%s/index.html", musica.getSlug()), content, "text/html");

    }

    private Map<String, Object> getContext() {
        Map<String, Object> context = Maps.newHashMap();
        context.put("STATICPATH", "https://static.musicasparamissa.com.br");

        context.put("partesComuns", new ArrayList<>());
        context.put("tempos", new ArrayList<>());
        context.put("solenidadesEFestas", new ArrayList<>());
        context.put("destaques", new ArrayList<>());
        context.put("posts", new ArrayList<>());
        context.put("current_year", Calendar.getInstance().get(Calendar.YEAR));

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

        StringBuilder result = new StringBuilder();

        //Get file from resources folder
        InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);

        Scanner scanner = new Scanner(in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            result.append(line).append("\n");
        }

        scanner.close();


        return result.toString();

    }

}
