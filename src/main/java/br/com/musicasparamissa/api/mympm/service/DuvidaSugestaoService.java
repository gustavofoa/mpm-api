package br.com.musicasparamissa.api.mympm.service;

import br.com.musicasparamissa.api.mympm.entity.DuvidaSugestao;
import br.com.musicasparamissa.api.mympm.repository.DuvidaSugestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("MyMpMDuvidaSugestaoService")
public class DuvidaSugestaoService {
	
	@Autowired
	private DuvidaSugestaoRepository duvidaSugestaoRepository;

	public Page<DuvidaSugestao> search(String filter, Pageable pageable) {
		return duvidaSugestaoRepository.findByTituloIgnoreCaseContainingOrTextoIgnoreCaseContaining(filter, filter, pageable);
	}

}
