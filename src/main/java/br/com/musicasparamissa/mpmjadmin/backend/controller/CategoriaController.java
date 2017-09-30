/* 
 * @(#)CategoriaController.java 1.0 08/2017
 */
package br.com.musicasparamissa.mpmjadmin.backend.controller;

import br.com.musicasparamissa.mpmjadmin.backend.entity.Categoria;
import br.com.musicasparamissa.mpmjadmin.backend.exception.InvalidEntityException;
import br.com.musicasparamissa.mpmjadmin.backend.exception.UnableToRemoveException;
import br.com.musicasparamissa.mpmjadmin.backend.service.CategoriaService;
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

        try {
            categoriaService.save(categoria);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidEntityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

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
