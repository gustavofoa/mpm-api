package br.com.musicasparamissa.api.mympm.service;

import br.com.musicasparamissa.api.mympm.entity.LogVindi;
import br.com.musicasparamissa.api.mympm.repository.LogVindiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("MyMpMLogVindiService")
public class LogVindiService {
	
	@Autowired
	private LogVindiRepository logVindiRepository;

	public Page<LogVindi> search(String filter, Boolean processed, Pageable pageable) {
		return logVindiRepository.search(filter, processed, pageable);
	}

}
