package br.com.musicasparamissa.api.controller;

import br.com.musicasparamissa.api.entity.Musica;
import br.com.musicasparamissa.api.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                               @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return musicaService.search(filter, pageable);

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
