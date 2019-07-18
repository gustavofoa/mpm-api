package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.entity.Categoria;
import br.com.musicasparamissa.api.exception.InvalidEntityException;
import br.com.musicasparamissa.api.exception.UnableToRemoveException;
import br.com.musicasparamissa.api.mpm.service.CategoriaService;
import br.com.musicasparamissa.api.mpm.service.ClearCacheService;
import br.com.musicasparamissa.api.mpm.service.SiteGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private SiteGenerateService siteGenerateService;

    @Autowired
    private ClearCacheService clearCacheService;

    @GetMapping("/search")
    public ResponseEntity<Set<Categoria>> search(@RequestParam("filter") String filter) {

        Set<Categoria> categorias = categoriaService.search(filter);

        return new ResponseEntity<>(categorias, HttpStatus.OK);

    }

    @GetMapping("/tree")
    public ResponseEntity<Set<Categoria>> getTree() {

        Set<Categoria> categoriasTree = categoriaService.getTree();

        return new ResponseEntity<>(categoriasTree, HttpStatus.OK);

    }

    @GetMapping("/{slug}")
    public ResponseEntity<Categoria> get(@PathVariable("slug") String slug) {

        Categoria categoria = categoriaService.getCategoria(slug);

        return new ResponseEntity<>(categoria, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Categoria categoria) {

        categoriaService.save(categoria);

        siteGenerateService.generateOnlyCategoria(categoria, siteGenerateService.getContext());
        clearCacheService.one("https://musicasparamissa.com.br/musicas-de/"+categoria.getSlug());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<String> delete(@PathVariable("slug") String slug) {

        try {
            categoriaService.delete(slug);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UnableToRemoveException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
