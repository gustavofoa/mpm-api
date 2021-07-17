package br.com.musicasparamissa.api.cc.controller;

import br.com.musicasparamissa.api.cc.dto.ArtistaDetailDto;
import br.com.musicasparamissa.api.cc.dto.ArtistaDto;
import br.com.musicasparamissa.api.cc.entity.Artista;
import br.com.musicasparamissa.api.cc.entity.Musica;
import br.com.musicasparamissa.api.cc.service.ArtistaService;
import br.com.musicasparamissa.api.cc.service.MusicaService;
import br.com.musicasparamissa.api.cc.service.SiteGenerateService;
import br.com.musicasparamissa.api.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController("CcApiSiteController")
@RequestMapping("/cifrascatolicas")
public class ApiSiteController {

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private MusicaService musicaService;

    @GetMapping("/artistas")
    public List<ArtistaDto> list() {

        return artistaService.list().stream()
                .map(artista -> new ArtistaDto(artista))
                .forEach(artistaDto ->
                        artistaDto.add(linkTo(methodOn(ApiSiteController.class).get(artistaDto.getId())
                        .withSelfRel()))
                )
                .collect(Collectors.toList());

    }

    @GetMapping("/artistas/{slug}")
    public ArtistaDetailDto get(@PathVariable String slug) throws NotFoundException {

        Artista artista = artistaService.getArtista(slug);
        if(artista == null)
            throw new NotFoundException();
        List<Musica> musicas = musicaService.findByArtista(slug);
        return new ArtistaDetailDto(artista, musicas);

    }

}
