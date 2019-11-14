package br.com.musicasparamissa.api.mympm.repository;

import br.com.musicasparamissa.api.mympm.entity.Compra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MyMpMCompraRepository")
public interface CompraRepository extends CrudRepository<Compra, Long> {

    List<Compra> findByIdUsuarioOrderByDataExpiracaoDesc(Long idUsuario);

}
