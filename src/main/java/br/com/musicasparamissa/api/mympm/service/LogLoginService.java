package br.com.musicasparamissa.api.mympm.service;

import br.com.musicasparamissa.api.mympm.entity.LogLogin;
import br.com.musicasparamissa.api.mympm.repository.LogLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("MyMpMLogLoginService")
public class LogLoginService {
	
	@Autowired
	private LogLoginRepository logLoginRepository;

	public Page<LogLogin> search(String filter, Pageable pageable) {
		return logLoginRepository.findByEmailIgnoreCaseContainingOrderByDataDesc(filter, pageable);
	}

}
