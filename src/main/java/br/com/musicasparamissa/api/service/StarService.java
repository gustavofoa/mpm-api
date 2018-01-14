package br.com.musicasparamissa.api.service;

import br.com.musicasparamissa.api.dto.Star;
import br.com.musicasparamissa.api.entity.Musica;
import br.com.musicasparamissa.api.exception.NotFoundException;
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

    public Musica vote(String slug, int stars) throws NotFoundException {

        Musica musica = musicaRepository.findOne(slug);
        if(musica == null)
            throw new NotFoundException();

        if(stars < 1     || stars > 5)
            return musica;

        musica.setRating(
                (musica.getRating() * musica.getVotes() + stars*100/5) /
                        (musica.getVotes() + 1));
        musica.setVotes(musica.getVotes() + 1);

        return musicaRepository.save(musica);

    }

}
