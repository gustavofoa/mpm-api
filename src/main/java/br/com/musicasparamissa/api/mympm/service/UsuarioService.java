package br.com.musicasparamissa.api.mympm.service;

import br.com.musicasparamissa.api.mympm.entity.Usuario;
import br.com.musicasparamissa.api.mympm.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MyMpMUsuarioService")
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Page<Usuario> search(String filter, Pageable pageable) {
		return usuarioRepository.findByEmailIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByDataCompraDesc(filter, filter, pageable);
	}

    public List<Usuario> list() {
        return usuarioRepository.findAllByOrderByNome();
    }

}
