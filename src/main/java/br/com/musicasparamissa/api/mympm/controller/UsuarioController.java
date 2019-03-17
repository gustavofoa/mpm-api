package br.com.musicasparamissa.api.mympm.controller;

import br.com.musicasparamissa.api.mympm.entity.Usuario;
import br.com.musicasparamissa.api.mympm.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("MyMpMUsuarioController")
@RequestMapping("/mympm/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> list() {

        return usuarioService.list();

    }

    @GetMapping("/search")
    public Page<Usuario> search(@RequestParam("filter") String filter,
                                @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return usuarioService.search(filter, pageable);

    }

}
