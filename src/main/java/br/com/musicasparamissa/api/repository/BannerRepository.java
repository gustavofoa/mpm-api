package br.com.musicasparamissa.api.repository;

import br.com.musicasparamissa.api.entity.Banner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends CrudRepository<Banner, Integer> {

}
