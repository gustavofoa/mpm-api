package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.entity.Musica;
import br.com.musicasparamissa.api.mpm.service.ClearCacheService;
import br.com.musicasparamissa.api.mpm.service.MusicaService;
import br.com.musicasparamissa.api.mpm.service.SiteGenerateService;
import br.com.musicasparamissa.api.mpm.service.SiteStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/musicas")
public class MusicaController {


    private final static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");


    @Autowired
    private MusicaService musicaService;

    @Autowired
    private SiteGenerateService siteGenerateService;

    @Autowired
    private SiteStorage siteStorage;

    @Autowired
    private ClearCacheService clearCacheService;

    @GetMapping(path = "/{slug}/exists")
    public ResponseEntity<String> exists(@PathVariable("slug") String slug) {

        if(musicaService.exists(slug))
            return new ResponseEntity<>("1", HttpStatus.OK);

        return new ResponseEntity<>("0", HttpStatus.OK);

    }

    @GetMapping("/search")
    public Page<Musica> search(@RequestParam("filter") String filter,
                               @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) throws UnsupportedEncodingException {

        return musicaService.search(URLDecoder.decode(
                filter,
                java.nio.charset.StandardCharsets.UTF_8.toString()), pageable);

    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Musica musica) {

        musicaService.save(musica);

        siteGenerateService.generateOnlyMusica(musica, siteGenerateService.getContext());
        clearCacheService.one("https://musicasparamissa.com.br/musica/"+musica.getSlug());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<String> delete(@PathVariable("slug") String slug) {

        Musica musica = musicaService.getMusica(slug);
        musicaService.delete(musica);

        musica.getCategorias().forEach(c -> siteGenerateService.generateOnlyCategoria(c, siteGenerateService.getContext()));

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/{slug}/audio/project")
    public ResponseEntity<String> postAudioProject(@PathVariable("slug") String slug, @RequestParam("file") MultipartFile file) {

        try {
            siteStorage.saveMpmjadminFile(String.format("mpm/musicas/%s/audio/project/%s.zip", slug, sdf.format(new Date())), file.getBytes());
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/{slug}/audio/file")
    public ResponseEntity<String> postAudioFile(@PathVariable("slug") String slug, @RequestParam("file") MultipartFile file) {

        try {
            siteStorage.saveMpmjadminFile(String.format("mpm/musicas/%s/audio/file/%s.mp3", slug, sdf.format(new Date())), file.getBytes());
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/{slug}/audio/project")
    public ResponseEntity<List<String>> getAudioProject(@PathVariable("slug") String slug) {

        List<String> files = siteStorage.listMpmjadminFile(String.format("mpm/musicas/%s/audio/project/", slug));

        return new ResponseEntity<>(files, HttpStatus.OK);

    }

    @GetMapping("/{slug}/audio/file")
    public ResponseEntity<List<String>> getAudioFile(@PathVariable("slug") String slug) {

        List<String> files = siteStorage.listMpmjadminFile(String.format("mpm/musicas/%s/audio/file/", slug));

        return new ResponseEntity<>(files, HttpStatus.OK);

    }

}
