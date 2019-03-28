package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mympm.entity.LogLogin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("MyMpMLogLoginRepository")
public interface LogLoginRepository extends CrudRepository<LogLogin, Integer> {

    Page<LogLogin> findByEmailIgnoreCaseContainingOrderByDataDesc(String login, Pageable pageable);

}
