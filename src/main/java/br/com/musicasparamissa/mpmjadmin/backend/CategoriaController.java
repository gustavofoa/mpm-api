/* 
 * @(#)CategoriaController.java 1.0 08/2017
 */
package br.com.musicasparamissa.mpmjadmin.backend;

import br.com.musicasparamissa.mpmjadmin.backend.entity.Categoria;
import br.com.musicasparamissa.mpmjadmin.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/tree")
	public ResponseEntity<Set<Categoria>> getTree(){

		Set<Categoria> categoriasTree = categoriaService.getTree();

		return new ResponseEntity<>(categoriasTree, HttpStatus.OK);

	}

}
