package br.com.musicasparamissa.api.mpm.service;

import br.com.musicasparamissa.api.mpm.entity.*;
import br.com.musicasparamissa.api.mpm.repository.*;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.loader.ResourceLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("MpmSiteGenerateService")
public class SiteGenerateService {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
    private HtmlCompressor compressor = new HtmlCompressor();

    private static final String TEMPLATE_PATH = "static.musicasparamissa.com.br/app/templates/";

    @Autowired
    private MusicaRepository musicaRepository;
    @Autowired
    private DiaLiturgicoRepository diaLiturgicoRepository;
    @Autowired
    private ItemLiturgiaRepository itemLiturgiaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private DataRepository dataRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ClearCacheService clearCacheService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private SiteStorage siteStorage;

    public void generateAll() {

        bannerService.refresh();

        Map<String, Object> context = getContext();

        Calendar tenDaysAgo = Calendar.getInstance();
        tenDaysAgo.add(Calendar.DATE, -10);
        Iterable<Data> datas = dataRepository.findAllByDataGreaterThanOrderByDataDesc(tenDaysAgo.getTime());
        Iterable<Musica> musicas = musicaRepository.findAll();

        generateHome();

        generateSitemap();

        generateDatas(datas);

        generateStars(musicas);

        for (Musica musica : musicas)
            generateOnlyMusica(musica, context);

        for (Categoria categoria : categoriaRepository.findAll())
            generateOnlyCategoria(categoria, context);

//        List<DiaLiturgico> diasLiturgicoWithData = new ArrayList<>();
//        datas.forEach(d -> diasLiturgicoWithData.add(d.getLiturgia()));
//        log.info("Refreshing {} diasLiturgicos.", diasLiturgicoWithData.size());
//        for (DiaLiturgico diaLiturgico : diasLiturgicoWithData)
        for (DiaLiturgico diaLiturgico : diaLiturgicoRepository.findAll())
            generateOnlyPaginaSugestao(diaLiturgico, context);


        //Single pages
        generateFromTemplate("search", null);
        generateFromTemplate("confirme-seu-email", "mail");
        generateFromTemplate("assinatura-confirmada", "mail");
        generateFromTemplate("desinscricao", "mail");
        generateFromTemplate("politica-de-privacidade", "pages");
        generateFromTemplate("404", null);

    }

    public void generateHome() {
        log.info("[MpM] Generating Home.");

        Map<String, Object> context = getContext();

        context.put("banner_footer", categoriaRepository.findOne("outras-missas").getBannerFooter());

        String content = renderTemplate(TEMPLATE_PATH + "index.html", context);

        content = compressor.compress(content);

        siteStorage.saveFile("index.html", content, "text/html");
        clearCacheService.one("https://musicasparamissa.com.br");
    }

    private void generateSitemap() {
        log.info("[MpM] Generating Sitemap.");

        Map<String, Object> context = getContext();

        context.put("musicas", musicaRepository.findAll());

        context.put("diasLiturgicos", diaLiturgicoRepository.findAll());

        context.put("categorias", categoriaRepository.findAll());

        String content = renderTemplate(TEMPLATE_PATH + "sitemap.xml", context);

        siteStorage.saveFile("sitemap.xml", content, "text/xml");
        clearCacheService.one("https://musicasparamissa.com.br/sitemap.xml");
    }

    public void generateDatas(Iterable<Data> datas) {
        log.info("[MpM] Generating Datas.");

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

        content.replace(content.length() - 1, content.length(), "");
        content.append("}");

        siteStorage.saveFile("datas.json", content.toString(), "application/json");
        clearCacheService.one("https://musicasparamissa.com.br/datas.json");
        clearCacheService.one("https://blog.musicasparamissa.com.br/datas.json");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Save datas/yyyy-mm-dd.json
        datas.forEach(d -> {
            StringBuilder pageContent = new StringBuilder("{");
            pageContent.append("\"slug\":\"");
            pageContent.append(d.getLiturgia().getSlug());
            pageContent.append("\",\"titulo\":\"");
            pageContent.append(d.getLiturgia().getTitulo().replaceAll("\"", "'"));
            pageContent.append("\",\"img\":\"");
            pageContent.append(d.getLiturgia().getImg());
            pageContent.append("\",\"img80\":\"");
            pageContent.append(d.getLiturgia().getImg80x80url());
            pageContent.append("\",\"img300\":\"");
            pageContent.append(d.getLiturgia().getImg300url());
            pageContent.append("\",\"imgUrl\":\"");
            pageContent.append(d.getLiturgia().getImgUrl());
            pageContent.append("\",\"introducao\":\"");
            pageContent.append(d.getLiturgia().getIntroducao());
            pageContent.append("\",\"items\":[ ");
            itemLiturgiaRepository.findByDiaLiturgicoOrderByPosicao(d.getLiturgia()).forEach(item -> {
                pageContent.append("{\"posicao\":\"");
                pageContent.append(item.getPosicao());
                pageContent.append("\",\"titulo\":\"");
                pageContent.append(item.getTitulo().replaceAll("\"", "'"));
                pageContent.append("\",\"descricao\":\"");
                pageContent.append(item.getDescricao());
                pageContent.append("\"");
                if(item instanceof Leitura) {
                    pageContent.append(",\"texto\":\"");
                    pageContent.append(item.getFormatedTexto().replaceAll("\"", "'"));
                    pageContent.append("\"");
                } else {
                    pageContent.append(",\"musicas\":[ ");
                    item.getMusicas().forEach(musica -> {
                        pageContent.append("{\"nome\":\"");
                        pageContent.append(musica.getNome().replaceAll("\"", "'"));
                        pageContent.append("\",\"slug\":\"");
                        pageContent.append(musica.getSlug());
                        pageContent.append("\",\"votes\":\"");
                        pageContent.append(musica.getVotes());
                        pageContent.append("\",\"rating\":\"");
                        pageContent.append(musica.getRating());
                        pageContent.append("\",\"videoCode\":\"");
                        pageContent.append(musica.getVideoCode());/*
                        pageContent.append("\",\"linkVideo\":\"");
                        pageContent.append(musica.getLinkVideo());
                        pageContent.append("\",\"letraInicio\":\"");
                        pageContent.append(musica.getLetraInicio());
                        pageContent.append("\",\"letra\":\"");
                        pageContent.append(musica.getLetra());
                        pageContent.append("\",\"cifra\":\"");
                        pageContent.append(musica.getCifra());
                        pageContent.append("\",\"info\":\"");
                        pageContent.append(musica.getInfo());
                        pageContent.append("\",\"disponivelDownload\":\"");
                        pageContent.append(musica.getDisponivelDownload());*/
                        pageContent.append("\"},");
                    });
                    pageContent.replace(pageContent.length() - 1, pageContent.length(), "]");
                }
                pageContent.append("},");
            });
            pageContent.replace(pageContent.length() - 1, pageContent.length(), "]}");

            siteStorage.saveFile("datas/"+sdf.format(d.getData())+".json", pageContent.toString(), "application/json");
        });

    }

    public void generateStars(Iterable<Musica> musicas) {
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

        content.replace(content.length() - 1, content.length(), "");
        content.append("}");

        siteStorage.saveFile("stars", compressor.compress(content.toString()), "application/json");
        clearCacheService.one("https://musicasparamissa.com.br/stars");
    }

    public void generateMusica(String slugMusica) {

        log.info("[MpM] Generating Musica page: " + slugMusica);

        Musica musica = musicaRepository.findOne(slugMusica);

        generateOnlyMusica(musica, getContext());

    }

    public void generateSugestoesPara(String slugDiaLiturgico) {

        log.info("[MpM] Generating DiaLiturgico page: " + slugDiaLiturgico);

        DiaLiturgico diaLiturgico = diaLiturgicoRepository.findOne(slugDiaLiturgico);

        generateOnlyPaginaSugestao(diaLiturgico, getContext());

    }

    public void generateMusicasDe(String slugCategoria) {

        log.info("[MpM] Generating Categoria page: " + slugCategoria);

        Categoria categoria = categoriaRepository.findOne(slugCategoria);

        generateOnlyCategoria(categoria, getContext());

    }

    public void generateOnlyCategoria(Categoria categoria, Map<String, Object> context) {
        log.info("Generating categoria: " + categoria.getSlug());

        context.put("categoria", categoria);

        if (categoria.getChildren() == null || categoria.getChildren().isEmpty())
            context.put("musicas", categoria.getMusicas());
        else
            Collections.sort(categoria.getChildren());

        if (categoria.getCategoriaMae() != null && categoria.getCategoriaMae().length() > 0)
            context.put("categoriaMae", categoriaRepository.findOne(categoria.getCategoriaMae()));
        else
            context.put("categoriaMae", null);

        context.put("banner_footer", categoria.getBannerFooter());
        context.put("banner_lateral", categoria.getBannerLateral());


        String content = renderTemplate(TEMPLATE_PATH + "musicas-de.html", context);

        content = compressor.compress(content);

        siteStorage.saveFile(String.format("musicas-de/%s/index.html", categoria.getSlug()), content, "text/html");
    }

    public void generateOnlyPaginaSugestao(DiaLiturgico diaLiturgico, Map<String, Object> context) {
        log.info("Generating paginaSugestao: " + diaLiturgico.getSlug());

        context.put("diaLiturgico", diaLiturgico);

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        Set<Data> datas = dataRepository.findAllByDataGreaterThanAndLiturgiaOrderByDataDesc(yesterday.getTime(), diaLiturgico);

        context.put("datas", datas);

        context.put("items", itemLiturgiaRepository.findByDiaLiturgicoOrderByPosicao(diaLiturgico));

        Calendar aMonthAgo = Calendar.getInstance();
        aMonthAgo.add(Calendar.DATE, -30);
        context.put("saturday", false);

        for (Data data : datas) {
            if(data.getData().getTime() < aMonthAgo.getTimeInMillis())
                continue;
            Calendar cal = Calendar.getInstance();
            cal.setTime(data.getData());
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                context.put("saturday", true);
        }

        if (diaLiturgico.getSlug().startsWith("vigilia-pascal"))
            context.put("saturday", false);

        context.put("banner_footer", diaLiturgico.getBannerFooter());
        context.put("banner_lateral", diaLiturgico.getBannerLateral());

        String content = renderTemplate(TEMPLATE_PATH + "sugestoes-para.html", context);

        content = compressor.compress(content);

        siteStorage.saveFile(String.format("sugestoes-para/%s/index.html", diaLiturgico.getSlug()), content, "text/html");
    }

    public void generateOnlyMusica(Musica musica, Map<String, Object> context) {
        log.info("Generating musica: " + musica.getSlug());

        Map<String, Object> musicaContext = Maps.newHashMap();
        context.put("musica", musicaContext);

        musicaContext.put("slug", musica.getSlug());
        musicaContext.put("nome", musica.getNome());
        musicaContext.put("letra", musica.getLetra().replace("\n", "<br/>"));
        musicaContext.put("letra_inicio", musica.getLetraInicio());
        musicaContext.put("cifra", musica.getCifra());
        musicaContext.put("info", musica.getInfo().replace("\n", "<br/>"));
        musicaContext.put("get_absolute_url", String.format("/musica/%s/", musica.getSlug()));
        if (musica.getLinkVideo() != null) {
            musicaContext.put("get_video_code", musica.getVideoCode());
        }
        String plural = "";
        if (musica.getVotes() == null)
            musica.setVotes(0);
        if (musica.getRating() == null)
            musica.setRating(0f);
        if (musica.getVotes() > 1)
            plural = "s";
        musicaContext.put("get_legend", String.format("<span property='ratingValue'>%.2f</span> em <span property='ratingCount'>%d</span> voto%s",
                musica.getRating() * 5 / 100.0, musica.getVotes(), plural));
        musicaContext.put("tem_imagem", musica.getTemImagem());

        context.put("banner_footer", musica.getBannerFooter());
        context.put("banner_lateral", musica.getBannerLateral());


        String content = renderTemplate(TEMPLATE_PATH + "musica.html", context);

        content = compressor.compress(content);

        siteStorage.saveFile(String.format("musica/%s/index.html", musica.getSlug()), content, "text/html");

    }

    public Map<String, Object> getContext() {

        Set<Categoria> catPartesComuns = categoriaRepository.findByCategoriaMaeOrderByOrdem("partes-comuns-da-missa");
        Set<Categoria> catTempos = categoriaRepository.findByCategoriaMaeAndSlugLikeOrderByOrdem(null, "tempo%");
        
        Map<String, Object> context = Maps.newHashMap();
        context.put("STATICPATH", "https://static.musicasparamissa.com.br");

        context.put("partesComuns", catPartesComuns);
        context.put("tempos", catTempos);
        context.put("destaques", dataRepository.findAllByDataGreaterThanAndDestaqueOrderByDataAsc(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime(), true));
//        context.put("posts", postRepository.findAll());
        context.put("current_year", Calendar.getInstance().get(Calendar.YEAR));

        return context;
    }

    private String renderTemplate(String templateName, Map<String, Object> context) {
        Jinjava jinjava = new Jinjava();

        jinjava.setResourceLocator(new ResourceLocator() {
            @Override
            public String getString(String s, Charset charset, JinjavaInterpreter jinjavaInterpreter) throws IOException {

                String filePath = TEMPLATE_PATH + s;

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


        String template = null;
        try {
            template = Resources.toString(Resources.getResource(fileName), Charsets.UTF_8);
        } catch (IOException e) {
            log.error("Error on getting template page.");
        }

        return template;

    }

    public void generateFromTemplate(String template, String folder) {

        Map<String, Object> context = getContext();

        context.put("banner_footer", categoriaRepository.findOne("outras-missas").getBannerFooter());

        String content = renderTemplate(TEMPLATE_PATH + (folder != null ? folder + "/" : "") + template + ".html", context);

        content = compressor.compress(content);

        siteStorage.saveFile(String.format("%s/index.html", template), content, "text/html");
        clearCacheService.one("https://musicasparamissa.com.br/"+template);
    }
}
