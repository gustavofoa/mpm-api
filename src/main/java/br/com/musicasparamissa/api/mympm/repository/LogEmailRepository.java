package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mympm.entity.LogEmail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("MyMpMLogEmailRepository")
public interface LogEmailRepository extends CrudRepository<LogEmail, Integer> {

}
