package br.com.musicasparamissa.api.mpm.service;

import br.com.musicasparamissa.api.exception.InvalidEntityException;
import br.com.musicasparamissa.api.mpm.entity.Banner;
import br.com.musicasparamissa.api.mpm.entity.Musica;
import br.com.musicasparamissa.api.mpm.entity.SugestaoMusica;
import br.com.musicasparamissa.api.mpm.repository.BannerRepository;
import br.com.musicasparamissa.api.mpm.repository.ItemLiturgiaRepository;
import br.com.musicasparamissa.api.mpm.repository.MusicaRepository;
import com.google.common.collect.Lists;
import nu.validator.messages.MessageEmitter;
import nu.validator.messages.MessageEmitterAdapter;
import nu.validator.messages.TextMessageEmitter;
import nu.validator.servlet.imagereview.ImageCollector;
import nu.validator.source.SourceCode;
import nu.validator.validation.SimpleDocumentValidator;
import nu.validator.xml.SystemErrErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class MusicaService {


    private String htmlTemplate = "<html><head><title></title></head><body>%s</body></html>";


    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
	private MusicaRepository musicaRepository;

    @Autowired
    private HtmlValidationService htmlValidationService;

	@Autowired
	private ItemLiturgiaRepository itemLiturgiaRepository;

    public boolean exists(String slug) {
        return musicaRepository.exists(slug);
    }

	public Page<Musica> search(String filter, Pageable pageable) {
		return musicaRepository.findBySlugIgnoreCaseContainingOrNomeIgnoreCaseContainingOrLetraIgnoreCaseContainingOrInfoIgnoreCaseContaining(filter, filter, filter, filter, pageable);
	}

    @Transactional
	public void save(Musica musica) throws InvalidEntityException {

        if(!htmlValidationService.validateHtml(String.format(htmlTemplate, musica.getLetra())) ||
           !htmlValidationService.validateHtml(String.format(htmlTemplate, musica.getCifra())) ||
           !htmlValidationService.validateHtml(String.format(htmlTemplate, musica.getInfo())))
            throw new InvalidEntityException("HTML inválido!");

        Musica musicaBD = musicaRepository.findOne(musica.getSlug());

        if(musicaBD != null)
            musica.setDisponivelDownload(musicaBD.getDisponivelDownload());

        if(musica.getDataCadastro() == null)
            musica.setDataCadastro(LocalDate.now());
        musica.setDataUltimaEdicao(LocalDate.now());

        List<Banner> banners = Lists.newArrayList(bannerRepository.findByAtivo(true));
        Collections.shuffle(banners);
        musica.setBannerFooter(banners.get(0));
        Collections.shuffle(banners);
        musica.setBannerLateral(banners.get(0));

        if(musica.getRating() == null)
            musica.setRating(100F);
        if(musica.getVotes() == null)
            musica.setVotes(1);

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

    public List<Musica> listInvalidHtml() {
        Iterable<Musica> musicas = musicaRepository.findAll();
        List<Musica> musicasWithInvalidHtml = new ArrayList<>();

        musicas.forEach(musica -> {
            if(!htmlValidationService.validateHtml(String.format(htmlTemplate, musica.getLetra())) ||
               !htmlValidationService.validateHtml(String.format(htmlTemplate, musica.getCifra())) ||
                    !htmlValidationService.validateHtml(String.format(htmlTemplate, musica.getInfo())))
                musicasWithInvalidHtml.add(musica);
        });
        return musicasWithInvalidHtml;

    }
}
