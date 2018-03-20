package br.com.musicasparamissa.api.mpm.repository;

import br.com.musicasparamissa.api.mpm.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ItemLiturgiaRepository extends CrudRepository<ItemLiturgia, Long> {

    Set<ItemLiturgia> findByDiaLiturgicoOrderByPosicao(DiaLiturgico diaLiturgico);

    @Query("select m from mpm_sugestaomusica m left join m.avulsas a left join m.remover r"
            + " where a = :musica or r = :musica")
    Set<SugestaoMusica> findByMusica(@Param("musica") Musica musica);


    @Query("select m from mpm_sugestaomusica m inner join m.categorias c where c = :categoria")
    List<SugestaoMusica> findByCategoria(@Param("categoria") Categoria categoria);
}
