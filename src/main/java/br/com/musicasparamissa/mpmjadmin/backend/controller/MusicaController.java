package br.com.musicasparamissa.mpmjadmin.backend.controller;

import br.com.musicasparamissa.mpmjadmin.backend.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

}
