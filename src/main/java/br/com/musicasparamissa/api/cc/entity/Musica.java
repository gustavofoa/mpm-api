package br.com.musicasparamissa.api.cc.entity;

import br.com.musicasparamissa.api.mpm.entity.Categoria;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity(name = "cc_musica")
public class Musica {

    @Id
    private String slug;
    private String nome;
    private String cifra;
    private String info;
    @Column(name = "link_video")
    private String linkVideo;
    @ManyToOne
    private Artista artista;

    //Stars
    private Float rating;
    private Integer votes;

    //Log
    private LocalDate dataCadastro;
    private LocalDate dataUltimaEdicao;


    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((slug == null) ? 0 : slug.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Musica other = (Musica) obj;
        if (slug == null) {
            if (other.slug != null)
                return false;
        } else if (!slug.equals(other.slug))
            return false;
        return true;
    }

}
