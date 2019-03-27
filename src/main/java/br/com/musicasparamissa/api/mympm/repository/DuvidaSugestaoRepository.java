package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mympm.entity.DuvidaSugestao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("MyMpMDuvidaSugestaoRepository")
public interface DuvidaSugestaoRepository extends CrudRepository<DuvidaSugestao, Integer> {

    Page<DuvidaSugestao> findByTituloIgnoreCaseContainingOrTextoIgnoreCaseContainingOrderByDataDesc(String titulo, String texto, Pageable pageable);

}
