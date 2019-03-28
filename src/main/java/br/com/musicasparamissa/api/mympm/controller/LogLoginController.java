package br.com.musicasparamissa.api.mympm.controller;

import br.com.musicasparamissa.api.mympm.entity.LogLogin;
import br.com.musicasparamissa.api.mympm.service.LogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("MyMpMLogLoginController")
@RequestMapping("/mympm/logs-login")
public class LogLoginController {

    @Autowired
    private LogLoginService logLoginService;

    @GetMapping("/search")
    public Page<LogLogin> search(@RequestParam("filter") String filter,
                                 @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return logLoginService.search(filter, pageable);

    }

}
