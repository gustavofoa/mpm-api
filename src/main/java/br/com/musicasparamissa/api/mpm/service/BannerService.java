package br.com.musicasparamissa.api.mpm.service;

import br.com.musicasparamissa.api.mpm.entity.Banner;
import br.com.musicasparamissa.api.mpm.repository.BannerRepository;
import br.com.musicasparamissa.api.mpm.repository.CategoriaRepository;
import br.com.musicasparamissa.api.mpm.repository.DiaLiturgicoRepository;
import br.com.musicasparamissa.api.mpm.repository.MusicaRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BannerService {
	
	@Autowired
	private BannerRepository bannerRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private MusicaRepository musicaRepository;

	@Autowired
	private DiaLiturgicoRepository diaLiturgicoRepository;

	public Iterable<Banner> list() {
		return bannerRepository.findAll();
	}

    @Transactional
	public void save(Banner banner) {
		bannerRepository.save(banner);
	}

    @Transactional
    public void delete(Integer id) {
        bannerRepository.delete(id);
    }

    public void refresh() {
		List<Banner> banners = Lists.newArrayList(this.listAtivos());

		log.info("Updating banners.");
		categoriaRepository.findAll().forEach(categoria -> {
			Collections.shuffle(banners);
			categoria.setBannerFooter(banners.get(0));
			Collections.shuffle(banners);
			categoria.setBannerLateral(banners.get(0));
			categoriaRepository.save(categoria);
		});

		musicaRepository.findAll().forEach(musica -> {
			Collections.shuffle(banners);
			musica.setBannerFooter(banners.get(0));
			Collections.shuffle(banners);
			musica.setBannerLateral(banners.get(0));
			musicaRepository.save(musica);
		});


		diaLiturgicoRepository.findAll().forEach(diaLiturgico -> {
			Collections.shuffle(banners);
			diaLiturgico.setBannerFooter(banners.get(0));
			Collections.shuffle(banners);
			diaLiturgico.setBannerLateral(banners.get(0));
			diaLiturgicoRepository.save(diaLiturgico);
		});

    }

    public Iterable<Banner> listAtivos() {
        return bannerRepository.findByAtivo(true);
    }
}
