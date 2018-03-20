package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.service.ClearCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clear-cache")
public class ClearCacheController {

    @Autowired
    private ClearCacheService clearCacheService;

    @GetMapping("/all")
    public ResponseEntity<Void> all(){

        if(clearCacheService.all())
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/one")
    public ResponseEntity<Void> one(@RequestParam("url") String url){

        if(clearCacheService.one(url))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
