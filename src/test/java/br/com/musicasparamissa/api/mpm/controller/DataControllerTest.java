package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.exception.InvalidEntityException;
import br.com.musicasparamissa.api.mpm.entity.Data;
import br.com.musicasparamissa.api.mpm.repository.DataRepository;
import br.com.musicasparamissa.api.mpm.service.DataService;
import br.com.musicasparamissa.api.mpm.service.SiteGenerateService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class DataControllerTest {

    @InjectMocks
    private DataController controller;

    @Mock
    private DataService dataService;

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SiteGenerateService siteGenerateService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListDatas() {

        Page<Data> datas = Mockito.mock(Page.class);

        Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(dataService.listDatas(pageable)).thenReturn(datas);

        Page<Data> response = controller.listDatas(pageable);

        assertSame(datas, response);

    }

    @Test
    public void testCreate() throws ParseException, InvalidEntityException {


        Data data = Mockito.mock(Data.class);

        Mockito.doNothing().when(dataService).create(data);

        ResponseEntity<String> response = controller.create(data);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testCreateError() throws ParseException, InvalidEntityException {


        Data data = Mockito.mock(Data.class);

        Mockito.doThrow(new InvalidEntityException("Erro teste")).when(dataService).create(data);

        ResponseEntity<String> response = controller.create(data);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro teste", response.getBody());

    }

    @Test
    public void testUpdate() throws ParseException, InvalidEntityException {


        Data data = Mockito.mock(Data.class);
        Date oldDate = Mockito.mock(Date.class);

        Mockito.doNothing().when(dataService).update(data, oldDate);

        ResponseEntity<String> response = controller.update(oldDate, data);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testUpdateError() throws ParseException, InvalidEntityException {


        Data data = Mockito.mock(Data.class);
        Date oldDate = Mockito.mock(Date.class);

        Mockito.doThrow(new InvalidEntityException("Erro teste")).when(dataService).update(data, oldDate);

        ResponseEntity<String> response = controller.update(oldDate, data);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro teste", response.getBody());

    }

    @Test
    public void testDelete() throws ParseException, InvalidEntityException {


        Date date = Mockito.mock(Date.class);

        Mockito.doNothing().when(dataService).delete(date);

        ResponseEntity<String> response = controller.delete(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}