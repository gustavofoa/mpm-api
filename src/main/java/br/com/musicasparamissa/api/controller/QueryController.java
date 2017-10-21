package br.com.musicasparamissa.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QueryController {

    @Autowired
    private QueryService queryService;

    @GetMapping(path = "/query")
    public ResponseEntity<Page<Map<String, String>>> query(String query) {
        return new ResponseEntity<>(queryService.query(query), HttpStatus.OK);
    }
}
