package br.com.musicasparamissa.api.cc.controller;

import br.com.musicasparamissa.api.cc.entity.Musica;
import br.com.musicasparamissa.api.cc.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("CcMusicaController")
@RequestMapping("/cc/musicas")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    @GetMapping(path = "/{artista}/{slug}/exists")
    public ResponseEntity<String> exists(@PathVariable("artista") String artista,
                                         @PathVariable("slug") String slug) {

        if(musicaService.exists(artista, slug))
            return new ResponseEntity<>("1", HttpStatus.OK);

        return new ResponseEntity<>("0", HttpStatus.OK);

    }

    @GetMapping("/search")
    public Page<Musica> search(@RequestParam("filter") String filter,
                               @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return musicaService.search(filter, pageable);

    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Musica musica) {

        musicaService.save(musica);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{artista}/{slug}/exists")
    public ResponseEntity<String> delete(@PathVariable("artista") String artista,
                                         @PathVariable("slug") String slug) {

        Musica musica = musicaService.getMusica(artista, slug);
        musicaService.delete(musica);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
