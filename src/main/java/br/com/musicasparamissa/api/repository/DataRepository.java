package br.com.musicasparamissa.api.repository;

import br.com.musicasparamissa.api.entity.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DataRepository extends CrudRepository<Data, Date> {

    Page<Data> findAllByOrderByDataDesc(Pageable pageable);

}
