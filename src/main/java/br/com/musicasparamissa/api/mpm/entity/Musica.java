package br.com.musicasparamissa.api.mpm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity(name = "mpm_musica")
public class Musica {

    @Id
    private String slug;
    private String nome;
    private String letra;
    private String cifra;
    private String info;
    @Column(name = "link_video")
    private String linkVideo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "mpm_musica_categorias", joinColumns = {@JoinColumn(name = "musica_id")}, inverseJoinColumns = {@JoinColumn(name = "categoria_id")})
    private Set<Categoria> categorias;

    //Stars
    private Float rating;
    private Integer votes;

    @Column(name = "link_lpsalmo")
    private String linkLpSalmo;
    @Column(name = "enviada_cc")
    private Boolean enviadaCC;

    //Log
    private LocalDate dataCadastro;
    private LocalDate dataUltimaEdicao;


    @Override
    public String toString() {
        return nome;
    }

    public String getLetraInicio() {
        if (letra == null) {
            return "";
        }
        final int tamanho = 100;
        if (letra.trim().length() < tamanho) {
            return letra.trim();
        }
        return letra.trim().substring(0, tamanho) + " ...";
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
