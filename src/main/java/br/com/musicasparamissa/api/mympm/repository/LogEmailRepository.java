package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mympm.entity.LogEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("MyMpMLogEmailRepository")
public interface LogEmailRepository extends CrudRepository<LogEmail, Integer> {

    Page<LogEmail> findByEmailIgnoreCaseContainingOrTitleIgnoreCaseContainingOrderByDataDesc(String email, String title, Pageable pageable);

}
