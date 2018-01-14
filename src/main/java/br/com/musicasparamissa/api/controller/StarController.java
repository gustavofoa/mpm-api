package br.com.musicasparamissa.api.controller;

import br.com.musicasparamissa.api.dto.Star;
import br.com.musicasparamissa.api.entity.Banner;
import br.com.musicasparamissa.api.service.BannerService;
import br.com.musicasparamissa.api.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/stars")
public class StarController {

    @Autowired
    private StarService starService;

    @GetMapping
    public ResponseEntity<Map<String, Star>> list(){
        return new ResponseEntity<>(starService.list(), HttpStatus.OK);
    }

    @PostMapping("/{musica_slug}")
    public ResponseEntity<String> vote(
            @PathVariable String musicaSlug,
            @RequestParam("stars") int stars) {

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
