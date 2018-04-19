package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.entity.Musica;
import br.com.musicasparamissa.api.mpm.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

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
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<String> delete(@PathVariable("slug") String slug) {

        Musica musica = musicaService.getMusica(slug);
        musicaService.delete(musica);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
