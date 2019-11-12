package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mympm.entity.LogVindi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("MyMpMLogVindiRepository")
public interface LogVindiRepository extends CrudRepository<LogVindi, Long> {

    @Query("select l from mympm_log_vindi_webhook l where (l.customerEmail like %?1% or l.log like %?1%) and l.processed = ?2 order by l.data desc")
    Page<LogVindi> search(String filter, Boolean processed, Pageable pageable);

}
