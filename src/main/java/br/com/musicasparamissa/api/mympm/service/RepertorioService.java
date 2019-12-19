package br.com.musicasparamissa.api.mympm.service;

import br.com.musicasparamissa.api.mympm.entity.Compra;
import br.com.musicasparamissa.api.mympm.entity.Repertorio;
import br.com.musicasparamissa.api.mympm.entity.Usuario;
import br.com.musicasparamissa.api.mympm.repository.CompraRepository;
import br.com.musicasparamissa.api.mympm.repository.RepertorioRepository;
import br.com.musicasparamissa.api.mympm.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MyMpMRepertorioService")
public class RepertorioService {
	
	@Autowired
	private RepertorioRepository repertorioRepository;

    public Page<Repertorio> list(String filter, Pageable pageable) {
        return repertorioRepository.findByTituloIgnoreCaseContainingOrderByDataDesc(filter, pageable);
    }

}
