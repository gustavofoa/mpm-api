package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.entity.Categoria;
import br.com.musicasparamissa.api.exception.InvalidEntityException;
import br.com.musicasparamissa.api.exception.UnableToRemoveException;
import br.com.musicasparamissa.api.mpm.service.CategoriaService;
import br.com.musicasparamissa.api.mpm.service.SiteGenerateService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CategoriaControllerTest {

    @InjectMocks
    private CategoriaController controller;

    @Mock
    private CategoriaService categoriaService;

    @Mock
    private SiteGenerateService siteGenerateService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearch() {

        Set<Categoria> categorias = new HashSet<>();

        Mockito.when(categoriaService.search("filter")).thenReturn(categorias);

        ResponseEntity<Set<Categoria>> response = controller.search("filter");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(categorias, response.getBody());

    }

    @Test
    public void testGetTree() {

        Set<Categoria> categoriasTree = new HashSet<>();

        Mockito.when(categoriaService.getTree()).thenReturn(categoriasTree);

        ResponseEntity<Set<Categoria>> response = controller.getTree();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(categoriasTree, response.getBody());

    }

    @Test
    public void testGet() {

        Categoria categoria = new Categoria();

        Mockito.when(categoriaService.getCategoria("slug")).thenReturn(categoria);

        ResponseEntity<Categoria> response = controller.get("slug");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(categoria, response.getBody());

    }

    @Test
    public void testSave() throws InvalidEntityException {

        Categoria categoria = new Categoria();

        Mockito.doNothing().when(categoriaService).save(categoria);

        ResponseEntity<String> response = controller.save(categoria);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(null, response.getBody());

    }

    @Test
    public void testErrorOnSave() throws InvalidEntityException {

        Categoria categoria = new Categoria();

        InvalidEntityException invalidEntityException = new InvalidEntityException("erro");

        Mockito.doThrow(invalidEntityException).when(categoriaService).save(categoria);

        ResponseEntity<String> response = controller.save(categoria);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertSame("erro", response.getBody());

    }

    @Test
    public void testDelete() throws UnableToRemoveException {

        Mockito.doNothing().when(categoriaService).delete("slug");

        ResponseEntity<String> response = controller.delete("slug");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(null, response.getBody());

    }

    @Test
    public void testErrorOnDelete() throws UnableToRemoveException {

        UnableToRemoveException unableToRemoveException = new UnableToRemoveException("erro");

        Mockito.doThrow(unableToRemoveException).when(categoriaService).delete("slug");

        ResponseEntity<String> response = controller.delete("slug");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertSame("erro", response.getBody());

    }

}