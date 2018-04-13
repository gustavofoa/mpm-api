package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.dto.Imagem;
import br.com.musicasparamissa.api.mpm.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/mpm/imagens")
public class ImagemController {

    @Autowired
    private ImagemService s3Service;

    @Value("${mpm_api.aws.s3.mpm.static_bucket}")
    private String bucket;

    @GetMapping
    public ResponseEntity<Set<Imagem>> list(){
        return new ResponseEntity<>(s3Service.list(bucket), HttpStatus.OK);
    }

}
