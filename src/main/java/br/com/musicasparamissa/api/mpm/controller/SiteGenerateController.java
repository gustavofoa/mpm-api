package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.service.BannerService;
import br.com.musicasparamissa.api.mpm.service.SiteGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("MpmSiteGenerateController")
    @RequestMapping("/mpm/site-generate")
public class SiteGenerateController {

    @Autowired
    private SiteGenerateService siteGenerateService;

    @Autowired
    private BannerService bannerService;

    @PostMapping
    public ResponseEntity<Void> generateAll() {

        bannerService.refresh();

        siteGenerateService.generateAll();

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/musica/{musica}")
    public ResponseEntity<Void> generateMusica(@PathVariable("musica") String musica) {

        siteGenerateService.generateMusica(musica);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/sugestoes-para/{diaLiturgico}")
    public ResponseEntity<Void> generateSugestoesPara(@PathVariable("diaLiturgico") String diaLiturgico) {

        siteGenerateService.generateSugestoesPara(diaLiturgico);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/musicas-de/{categoria}")
    public ResponseEntity<Void> generateMusicasDe(@PathVariable("categoria") String categoria) {

        siteGenerateService.generateMusicasDe(categoria);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/template/{template}")
    public ResponseEntity<Void> generateFromTemplate(@PathVariable("template") String template, @RequestParam(value = "folder", required = false) String folder) {

        siteGenerateService.generateFromTemplate(template, folder);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
