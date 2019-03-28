package br.com.musicasparamissa.api.mympm.controller;

import br.com.musicasparamissa.api.mympm.entity.Notificacao;
import br.com.musicasparamissa.api.mympm.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("MyMpMNotificacaoController")
@RequestMapping("/mympm/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping("/search")
    public Page<Notificacao> search(@RequestParam("filter") String filter,
                                    @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return notificacaoService.search(filter, pageable);

    }

}
