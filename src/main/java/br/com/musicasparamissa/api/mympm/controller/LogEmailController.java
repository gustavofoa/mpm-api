package br.com.musicasparamissa.api.mympm.controller;

import br.com.musicasparamissa.api.mympm.entity.LogEmail;
import br.com.musicasparamissa.api.mympm.service.LogEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("MyMpMLogEmailController")
@RequestMapping("/mympm/logs-email")
public class LogEmailController {

    @Autowired
    private LogEmailService logEmailService;

    @GetMapping("/search")
    public Page<LogEmail> search(@RequestParam("filter") String filter,
                                @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return logEmailService.search(filter, pageable);

    }

}
