package br.com.musicasparamissa.api.controller;

import br.com.musicasparamissa.api.entity.Categoria;
import br.com.musicasparamissa.api.exception.InvalidEntityException;
import br.com.musicasparamissa.api.exception.UnableToRemoveException;
import br.com.musicasparamissa.api.service.CategoriaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class QueryControllerTest {

    @InjectMocks
    private QueryController controller;

    @Mock
    private QueryService queryService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQuery() {

        String query = "query";

        Page<Map<String, String>> dynamicResponseSet = Mockito.mock(Page.class);

        Mockito.when(queryService.query(query)).thenReturn(dynamicResponseSet);

        ResponseEntity<Page<Map<String, String>>> response = controller.query("query");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(dynamicResponseSet, response.getBody());

    }

}