package br.com.musicasparamissa.api.mympm.service;

import br.com.musicasparamissa.api.mympm.entity.MinhaMusica;
import br.com.musicasparamissa.api.mympm.repository.MinhaMusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MyMpMMinhaMusicaService")
public class MinhaMusicaService {
	
	@Autowired
	private MinhaMusicaRepository minhaMusicaRepository;

    public List<MinhaMusica> listByUser(Long idUsuario) {
        return minhaMusicaRepository.findByUsuarioId(idUsuario);
    }

}
