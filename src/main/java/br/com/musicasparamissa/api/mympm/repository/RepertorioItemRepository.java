package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mpm.entity.ItemLiturgia;
import br.com.musicasparamissa.api.mympm.entity.RepertorioItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MyMpMRepertorioItemRepository")
public interface RepertorioItemRepository extends CrudRepository<RepertorioItem, Long> {

    List<RepertorioItem> findByItemLiturgia(ItemLiturgia itemLiturgia);

}
