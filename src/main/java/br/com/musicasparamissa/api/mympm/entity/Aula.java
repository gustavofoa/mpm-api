package br.com.musicasparamissa.api.mympm.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "mympm_aula_curso")
public class Aula {

    @Id
    private Long id;
    private Integer numero;
    private String titulo;
    private String descricao;
    private String video;
    private Integer idCurso;

}
