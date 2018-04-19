package br.com.musicasparamissa.api.mpm.repository;

import br.com.musicasparamissa.api.mpm.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, String> {

}
