package br.com.musicasparamissa.api.mympm.entity;

import br.com.musicasparamissa.api.util.DateJsonDeserializer;
import br.com.musicasparamissa.api.util.DateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "mympm_minhasmusicas")
public class MinhaMusica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @Column(name = "slug_mpm")
    private String slugMpm;

    private String nome;

    private String letra;

    private String cifra;

    private String info;

    @Column(name = "link_video")
    private String linkVideo;

}
