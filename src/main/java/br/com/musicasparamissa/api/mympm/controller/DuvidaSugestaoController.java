package br.com.musicasparamissa.api.mympm.controller;

import br.com.musicasparamissa.api.mympm.entity.DuvidaSugestao;
import br.com.musicasparamissa.api.mympm.service.DuvidaSugestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("MyMpMDuvidaSugestaoController")
@RequestMapping("/mympm/duvidas-sugestoes")
public class DuvidaSugestaoController {

    @Autowired
    private DuvidaSugestaoService duvidaSugestaoService;

    @GetMapping("/search")
    public Page<DuvidaSugestao> search(@RequestParam("filter") String filter,
                                       @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return duvidaSugestaoService.search(filter, pageable);

    }

}
