package br.com.musicasparamissa.api.mpm.repository;

import br.com.musicasparamissa.api.mpm.entity.Data;
import br.com.musicasparamissa.api.mpm.entity.DiaLiturgico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Repository
public interface DataRepository extends CrudRepository<Data, Date> {

    Iterable<Data> findAllByDataGreaterThanOrderByDataDesc(Date date);

    Iterable<Data> findAllByDataGreaterThanAndDestaqueOrderByDataDesc(Date date, Boolean destaque);

    Page<Data> findAllByOrderByDataDesc(Pageable pageable);

    Set<Data> findAllByDataGreaterThanAndLiturgiaOrderByDataDesc(Date yesterday, DiaLiturgico diaLiturgico);

}
