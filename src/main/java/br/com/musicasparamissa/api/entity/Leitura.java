package br.com.musicasparamissa.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Getter
@Setter
@Entity(name = "mpm_leitura")
@PrimaryKeyJoinColumn(name = "itemliturgia_ptr_id")
public class Leitura extends ItemLiturgia {

    @Column(name = "marcacao_biblia")
    private String marcacaoBiblia;
    private String texto;

}
