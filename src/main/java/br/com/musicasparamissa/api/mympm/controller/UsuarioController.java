package br.com.musicasparamissa.api.mympm.controller;

import br.com.musicasparamissa.api.mympm.entity.*;
import br.com.musicasparamissa.api.mympm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("MyMpMUsuarioController")
@RequestMapping("/mympm/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RepertorioService repertorioService;

    @Autowired
    private MinhaMusicaService minhaMusicaService;

    @Autowired
    private DuvidaSugestaoService duvidaSugestaoService;

    @Autowired
    private LogEmailService logEmailService;

    @Autowired
    private AulaService aulaService;

    @GetMapping
    public List<Usuario> list() {

        return usuarioService.list();

    }

    @PutMapping("/{id}")
    public void save(@PathVariable Long id, @RequestBody Usuario usuario) {

        usuarioService.save(id, usuario);

    }

    @PutMapping("/update-mautic-segments")
    public void updateMauticSegments() {

        usuarioService.updateMauticSegments();

    }

    @GetMapping("/search")
    public Page<Usuario> search(@RequestParam("filter") String filter,
                                @RequestParam("premium") Boolean premium,
                                @RequestParam("expired") Boolean expired,
                                @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return usuarioService.search(filter, premium, expired, pageable);

    }

    @PutMapping("/{id}/premium/{premium}")
    public void setProcessed(
            @PathVariable("id") Long id,
            @PathVariable("premium") Boolean premium) {

        usuarioService.updatePremium(id, premium);

    }

    @GetMapping("/{id}/compras")
    public List<Compra> getCompras(@PathVariable("id") Long idUsuario) {

        return usuarioService.listCompras(idUsuario);

    }

    @PostMapping("/{id}/compras")
    public void saveCompra(@PathVariable Long id, @RequestBody Compra compra) {

        usuarioService.saveCompra(id, compra);

    }


    @DeleteMapping("/{idUsuario}/compras/{idCompra}")
    public void deleteCompra(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idCompra") Long idCompra) {

        usuarioService.deleteCompra(idUsuario, idCompra);

    }

    @GetMapping("/{idUsuario}/repertorios")
    public List<Repertorio> listRepertoriosByUser(@PathVariable("idUsuario") Long idUsuario) {

        return repertorioService.listByUser(idUsuario);

    }

    @GetMapping("/{idUsuario}/minhas-musicas")
    public List<MinhaMusica> listMinhasMusicasByUser(@PathVariable("idUsuario") Long idUsuario) {

        return minhaMusicaService.listByUser(idUsuario);

    }

    @GetMapping("/{idUsuario}/duvidas-sugestoes")
    public List<DuvidaSugestao> listDuvidasSugestoesByUser(@PathVariable("idUsuario") Long idUsuario) {

        return duvidaSugestaoService.listByUser(idUsuario);

    }

    @GetMapping("/{idUsuario}/emails")
    public List<LogEmail> listEmailsByUser(@PathVariable("idUsuario") Long idUsuario) {

        return logEmailService.listByUser(idUsuario);

    }

    @GetMapping("/{idUsuario}/aulas-assistidas")
    public List<Aula> listAulasAssistidasByUser(@PathVariable("idUsuario") Long idUsuario) {

        return aulaService.listByUser(idUsuario);

    }

}