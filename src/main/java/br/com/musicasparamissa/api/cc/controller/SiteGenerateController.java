package br.com.musicasparamissa.api.cc.controller;

import br.com.musicasparamissa.api.cc.service.SiteGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("CcSiteGenerateController")
@RequestMapping("/cc/site-generate")
public class SiteGenerateController {

    @Autowired
    private SiteGenerateService siteGenerateService;

    @PostMapping(path = "/")
    public ResponseEntity<Void> generateAll() {

        siteGenerateService.generateAll();

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/artista/{artista}")
    public ResponseEntity<Void> generateArtista(@PathVariable("artista") String artista) {

        siteGenerateService.generateArtista(artista);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/musica/{musica}")
    public ResponseEntity<Void> generateMusica(@PathVariable("musica") String musica) {

        siteGenerateService.generateMusica(musica);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
