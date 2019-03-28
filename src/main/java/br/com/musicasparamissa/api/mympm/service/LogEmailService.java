package br.com.musicasparamissa.api.mympm.service;

import br.com.musicasparamissa.api.mympm.entity.LogEmail;
import br.com.musicasparamissa.api.mympm.entity.Usuario;
import br.com.musicasparamissa.api.mympm.repository.LogEmailRepository;
import br.com.musicasparamissa.api.mympm.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MyMpMLogEmailService")
public class LogEmailService {
	
	@Autowired
	private LogEmailRepository logEmailRepository;

	public Page<LogEmail> search(String filter, Pageable pageable) {
		return logEmailRepository.findByEmailIgnoreCaseContainingOrTitleIgnoreCaseContainingOrderByDataDesc(filter, filter, pageable);
	}

}
