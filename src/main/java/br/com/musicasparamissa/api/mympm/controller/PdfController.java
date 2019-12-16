package br.com.musicasparamissa.api.mympm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("MyMpMPdfController")
@RequestMapping("/mympm/pdf")
public class PdfController {

    @GetMapping
    public void generate(@RequestBody String html){

        System.out.println(html);
    }

}