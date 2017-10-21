package br.com.musicasparamissa.api.controller;

import br.com.musicasparamissa.api.service.QueryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
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
    public void testQuery() throws SQLException {

        String query = "query";

        Set<Map<String, String>> dynamicResponseSet = new HashSet<>();

        Mockito.when(queryService.query(query)).thenReturn(dynamicResponseSet);

        ResponseEntity<Set<Map<String, String>>> response = controller.query("query");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(dynamicResponseSet, response.getBody());

    }

}