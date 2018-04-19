package br.com.musicasparamissa.api.mpm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "mpm_post")
public class Post {

    @Id
    private String url;
    private String titulo;
    private String imagem;
    private String autor;

}
