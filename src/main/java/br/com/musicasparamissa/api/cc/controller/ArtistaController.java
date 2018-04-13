package br.com.musicasparamissa.api.cc.controller;

import br.com.musicasparamissa.api.cc.entity.Artista;
import br.com.musicasparamissa.api.cc.entity.Musica;
import br.com.musicasparamissa.api.cc.service.ArtistaService;
import br.com.musicasparamissa.api.cc.service.MusicaService;
import br.com.musicasparamissa.api.cc.service.SiteGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("CcArtistaController")
@RequestMapping("/cc/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private MusicaService musicaService;

    @Autowired
    private SiteGenerateService siteGenerateService;

    @GetMapping(path = "/{slug}/exists")
    public ResponseEntity<String> exists(@PathVariable("slug") String slug) {

        if(artistaService.exists(slug))
            return new ResponseEntity<>("1", HttpStatus.OK);

        return new ResponseEntity<>("0", HttpStatus.OK);

    }

    @GetMapping("/{slug}/musicas")
    public ResponseEntity<List<Musica>> musicas(@PathVariable("slug") String slug) {

        List<Musica> musicas = musicaService.findByArtista(slug);

        if(musicas.isEmpty())
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(musicas, HttpStatus.OK);

    }

    @GetMapping
    public List<Artista> search() {

        return artistaService.list();

    }

    @GetMapping("/search")
    public Page<Artista> search(@RequestParam("filter") String filter,
                                @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return artistaService.search(filter, pageable);

    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Artista artista) {

        artistaService.save(artista);

        siteGenerateService.generateArtista(artista.getSlug());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<String> delete(@PathVariable("slug") String slug) {

        Artista artista = artistaService.getArtista(slug);
        artistaService.delete(artista);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
