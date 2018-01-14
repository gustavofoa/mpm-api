package br.com.musicasparamissa.api.service;

import br.com.musicasparamissa.api.dto.Star;
import br.com.musicasparamissa.api.entity.Musica;
import br.com.musicasparamissa.api.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StarService {

    @Autowired
    private MusicaRepository musicaRepository;

    public Map<String, Star> list() {

        final Map<String, Star> map = new HashMap <>();

        for(Musica musica : musicaRepository.findAll())
            map.put(musica.getSlug(), new Star(musica.getRating(), musica.getVotes()));


        return map;
    }
}
