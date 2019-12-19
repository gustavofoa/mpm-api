package br.com.musicasparamissa.api.mympm.controller;

import br.com.musicasparamissa.api.mympm.entity.Repertorio;
import br.com.musicasparamissa.api.mympm.service.RepertorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("MyMpMRepertorioController")
@RequestMapping("/mympm/repertorios")
public class RepertorioController {

    @Autowired
    private RepertorioService repertorioService;

    @GetMapping
    public Page<Repertorio> list(@RequestParam("filter") String filter,
                                 @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return repertorioService.list(filter, pageable);

    }

}
