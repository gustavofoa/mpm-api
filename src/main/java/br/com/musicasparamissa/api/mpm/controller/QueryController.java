package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Set;
import java.util.Map;

@RestController
public class QueryController {

    @Autowired
    private QueryService queryService;

    @GetMapping(path = "/query")
    public ResponseEntity<Set<Map<String, String>>> query(@RequestParam("query") String query) throws SQLException {
        return new ResponseEntity<>(queryService.query(query), HttpStatus.OK);
    }
}
