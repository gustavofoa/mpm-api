package br.com.musicasparamissa.api.mympm.entity;

import br.com.musicasparamissa.api.mpm.entity.ItemLiturgia;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "mympm_repertorio_item")
public class RepertorioItem {

    @Id
    private Long id;

    private Long ordem;

    private String titulo;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "id_repertorio", referencedColumnName = "id")
    private Repertorio repertorio;

    @ManyToOne
    @JoinColumn(name = "id_item_liturgia", referencedColumnName = "id")
    private ItemLiturgia itemLiturgia;

}
