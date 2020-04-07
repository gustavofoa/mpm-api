package br.com.musicasparamissa.api.mympm.controller;

import br.com.musicasparamissa.api.mympm.entity.Aula;
import br.com.musicasparamissa.api.mympm.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    public List<Aula> listByUser(Long idUsuario) {
        return aulaRepository.getAulaByUsuario(idUsuario);
    }

}
