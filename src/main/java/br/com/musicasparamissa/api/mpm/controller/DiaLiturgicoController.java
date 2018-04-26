package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.entity.DiaLiturgico;
import br.com.musicasparamissa.api.mpm.entity.ItemLiturgia;
import br.com.musicasparamissa.api.mpm.entity.Musica;
import br.com.musicasparamissa.api.mpm.entity.SugestaoMusica;
import br.com.musicasparamissa.api.mpm.service.ClearCacheService;
import br.com.musicasparamissa.api.mpm.service.DiaLiturgicoService;
import br.com.musicasparamissa.api.mpm.service.SiteGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;

@RestController
@RequestMapping("/dias-liturgicos")
public class DiaLiturgicoController {

    @Autowired
    private DiaLiturgicoService diaLiturgicoService;

    @Autowired
    private SiteGenerateService siteGenerateService;

    @Autowired
    private ClearCacheService clearCacheService;

    @GetMapping("/{slug}")
    public ResponseEntity<DiaLiturgico> getDiaLiturgico(@PathVariable("slug") String slug) {

        DiaLiturgico diaLiturgico = diaLiturgicoService.getDiaLiturgico(slug);

        return new ResponseEntity<>(diaLiturgico, HttpStatus.OK);

    }

    @GetMapping(path = "/{slug}/exists")
    public ResponseEntity<String> exists(@PathVariable("slug") String slug) {

        if(diaLiturgicoService.exists(slug))
            return new ResponseEntity<>("1", HttpStatus.OK);

        return new ResponseEntity<>("0", HttpStatus.OK);

    }

    @GetMapping("/search")
    public Page<DiaLiturgico> search(@RequestParam("filter") String filter,
                                     @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) throws UnsupportedEncodingException {

        return diaLiturgicoService.search(URLDecoder.decode(
                filter,
                java.nio.charset.StandardCharsets.UTF_8.toString()), pageable);

    }

    @GetMapping("/{slug}/items")
    public ResponseEntity<Set<ItemLiturgia>> listItems(@PathVariable("slug") String slug) {

        DiaLiturgico diaLiturgico = diaLiturgicoService.getDiaLiturgico(slug);
        Set<ItemLiturgia> items = diaLiturgicoService.listItems(diaLiturgico);

        return new ResponseEntity<>(items, HttpStatus.OK);

    }

    @GetMapping("/{slug}/items/{item}")
    public ResponseEntity<ItemLiturgia> getItemLiturgia(@PathVariable("slug") String slug,
                                                            @PathVariable("item") Long item) {

        ItemLiturgia itemLiturgia = diaLiturgicoService.getItemLiturgia(item);

        return new ResponseEntity<>(itemLiturgia, HttpStatus.OK);

    }

    @GetMapping("/{slug}/items/{item}/musicas")
    public ResponseEntity<Set<Musica>> listMusicasSugeridas(@PathVariable("slug") String slug,
                                                            @PathVariable("item") Long item) {

        SugestaoMusica sugestaoMusica = (SugestaoMusica) diaLiturgicoService.getItemLiturgia(item);
        Set<Musica> musicas = diaLiturgicoService.listMusicasSugeridas(sugestaoMusica);

        return new ResponseEntity<>(musicas, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody DiaLiturgico diaLiturgico) {

        diaLiturgicoService.save(diaLiturgico);

        siteGenerateService.generateOnlyPaginaSugestao(diaLiturgico, siteGenerateService.getContext());
        clearCacheService.one("https://musicasparamissa.com.br/sugestoes-para/"+diaLiturgico.getSlug());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/{slug}/items")
    public ResponseEntity<ItemLiturgia> save(@RequestBody ItemLiturgia item) {

        ItemLiturgia itemLiturgia = diaLiturgicoService.save(item);
        return new ResponseEntity<>(itemLiturgia, HttpStatus.OK);

    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<String> delete(@PathVariable("slug") String slug) {

        DiaLiturgico diaLiturgico = diaLiturgicoService.getDiaLiturgico(slug);
        diaLiturgicoService.delete(diaLiturgico);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{slug}/items/{item}")
    public ResponseEntity<String> delete(@PathVariable("item") Long id) {

        ItemLiturgia item = diaLiturgicoService.getItemLiturgia(id);
        diaLiturgicoService.delete(item);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
