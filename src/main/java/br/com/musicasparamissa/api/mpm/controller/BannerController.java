package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.entity.Banner;
import br.com.musicasparamissa.api.mpm.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public ResponseEntity<Iterable<Banner>> list(){
        return new ResponseEntity<>(bannerService.list(), HttpStatus.OK);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Iterable<Banner>> listActive(){
        return new ResponseEntity<>(bannerService.listAtivos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Banner banner) {

        bannerService.save(banner);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {

        bannerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/refresh")
    public ResponseEntity<Void> refresh(){

        bannerService.refresh();
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

}
