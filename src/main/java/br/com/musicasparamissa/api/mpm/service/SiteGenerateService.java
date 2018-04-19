package br.com.musicasparamissa.api.mpm.service;

import br.com.musicasparamissa.api.mpm.entity.Categoria;
import br.com.musicasparamissa.api.mpm.entity.Data;
import br.com.musicasparamissa.api.mpm.entity.Musica;
import br.com.musicasparamissa.api.mpm.repository.*;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("MpmSiteGenerateService")
public class SiteGenerateService {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    private static final String TEMPLATE_PATH = "static.musicasparamissa.com.br/app/templates/";

    @Autowired
    private MusicaRepository musicaRepository;
    @Autowired
    private DiaLiturgicoRepository diaLiturgicoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private DataRepository dataRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired()
    private SiteStorage siteStorage;

	public void generateAll(){

        Map<String, Object> context = getContext();

        Calendar tenDaysAgo = Calendar.getInstance();
        tenDaysAgo.add(Calendar.DATE, -10);
        Iterable<Data> datas = dataRepository.findAllByDataGreaterThanOrderByDataDesc(tenDaysAgo.getTime());
        Iterable<Musica> musicas = musicaRepository.findAll();

	    generateHome();

        generateSitemap();

        generateDatas(datas);

        generateStars(musicas);

//        for(Musica musica : musicas)
//            generateOnlyMusica(musica, context);

    }

    public void generateHome(){
        log.info("[MpM] Generating Home.");

        Map<String, Object> context = getContext();

        context.put("banner_footer", categoriaRepository.findOne("outras-missas").getBannerFooter());

        String content = renderTemplate(TEMPLATE_PATH + "index.html", context);

        siteStorage.saveFile("index.html", content, "text/html");
    }

    private void generateSitemap() {
        log.info("[MpM] Generating Sitemap.");

        Map<String, Object> context = getContext();

        context.put("musicas", musicaRepository.findAll());

        context.put("diasLiturgicos", diaLiturgicoRepository.findAll());

        context.put("categorias", categoriaRepository.findAll());

        String content = renderTemplate(TEMPLATE_PATH + "sitemap.xml", context);

        siteStorage.saveFile("sitemap.xml", content, "text/xml");
    }

    public void generateDatas(Iterable<Data> datas){
        log.info("[MpM] Generating Stars.");

        StringBuilder content = new StringBuilder("{");

        datas.forEach(d -> {
            content.append("\"");
            content.append(SDF.format(d.getData()));
            content.append("\":{\"destaque\":");
            content.append(d.getDestaque());
            content.append(",\"title\":\"");
            content.append(d.getLiturgia().getTitulo());
            content.append("\",\"url\":\"/sugestoes-para/");
            content.append(d.getLiturgia().getSlug());
            content.append("\"},");
        });

        content.replace(content.length()-1, content.length(),"");
        content.append("}");

        siteStorage.saveFile("datas.json", content.toString(), "application/json");
    }

    public void generateStars(Iterable<Musica> musicas){
        log.info("[MpM] Generating Stars.");

        StringBuilder content = new StringBuilder("{");

        musicas.forEach(m -> {
            content.append("\"");
            content.append(m.getSlug());
            content.append("\":{\"r\":");
            content.append(m.getRating());
            content.append(",\"v\":");
            content.append(m.getVotes());
            content.append("},");
        });

        content.replace(content.length()-1, content.length(),"");
        content.append("}");

        siteStorage.saveFile("stars", content.toString(), "application/json");
    }

    public void generateMusica(String slugMusica){

        log.info("[MpM] Generating Musica page: " + slugMusica);

        Musica musica = musicaRepository.findOne(slugMusica);

        generateOnlyMusica(musica, getContext());

    }

    public void generateSugestoesPara(String diaLiturgico) {
    }

    public void generateMusicasDe(String categoria) {
    }

    private void generateOnlyMusica(Musica musica, Map<String, Object> context) {


        Map<String, Object> musicaContext = Maps.newHashMap();
        context.put("musica", musicaContext);

        musicaContext.put("slug", musica.getSlug());
        musicaContext.put("nome", musica.getNome());
        musicaContext.put("letra", musica.getLetra().replace("\n", "<br/>"));
        musicaContext.put("cifra", musica.getCifra());
        musicaContext.put("info", musica.getInfo().replace("\n", "<br/>"));
        musicaContext.put("get_absolute_url", String.format("/musica/%s/", musica.getSlug()));
        musicaContext.put("get_video_code", musica.getLinkVideo().substring(
                musica.getLinkVideo().lastIndexOf('/')).replace("embed","").replace("watch?v=","").replace("v=",""));
        String plural = "";
        if(musica.getVotes() > 1)
            plural = "s";
        musicaContext.put("get_legend", String.format("<span property='ratingValue'>%.2f</span> em <span property='ratingCount'>%d</span> voto%s",
                musica.getRating() * 5 / 100.0, musica.getVotes(), plural));
        musicaContext.put("tem_imagem", musica.getTemImagem());

        context.put("banner_footer", musica.getBannerFooter());
        context.put("banner_lateral", musica.getBannerLateral());



        String content = renderTemplate(TEMPLATE_PATH + "musica.html", context);

        siteStorage.saveFile(String.format("musica/%s/index.html", musica.getSlug()), content, "text/html");

    }

    private Map<String, Object> getContext() {

        Set<Categoria> catPartesComuns = categoriaRepository.findByCategoriaMaeOrderByOrdem("partes-comuns-da-missa");
        Set<Categoria> catSolenidadesEFestas = categoriaRepository.findByCategoriaMaeOrderByOrdem("solenidades-e-festas");
        Set<Categoria> catTempos = categoriaRepository.findByCategoriaMaeAndSlugLikeOrderByOrdem(null, "tempo%");

        Map<String, Object> context = Maps.newHashMap();
        context.put("STATICPATH", "https://static.musicasparamissa.com.br");

        context.put("partesComuns", catPartesComuns);
        context.put("tempos", catTempos);
        context.put("solenidadesEFestas", catSolenidadesEFestas);
        context.put("destaques", dataRepository.findAllByDataGreaterThanAndDestaqueOrderByDataDesc(new Date(), true));
//        context.put("posts", postRepository.findAll());
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
