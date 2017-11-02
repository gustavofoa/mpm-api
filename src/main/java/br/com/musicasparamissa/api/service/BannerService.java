package br.com.musicasparamissa.api.service;

import br.com.musicasparamissa.api.entity.Banner;
import br.com.musicasparamissa.api.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BannerService {
	
	@Autowired
	private BannerRepository bannerRepository;

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

}
