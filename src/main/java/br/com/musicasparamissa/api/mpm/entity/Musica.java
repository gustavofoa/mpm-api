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

    public Musica() {
        this.votes = 0;
        this.rating = 0f;
        this.temImagem = false;
    }

    @Id
    private String slug;
    private String nome;
    private String letra;
    private String cifra;
    private String info;
    @Column(name = "link_video")
    private String linkVideo;
    @Column(name = "tem_imagem", nullable = false)
    private Boolean temImagem;

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

    @ManyToOne
    @JoinColumn(name = "banner_footer_id", referencedColumnName = "id")
    private Banner bannerFooter;
    @ManyToOne
    @JoinColumn(name = "banner_lateral_id", referencedColumnName = "id")
    private Banner bannerLateral;

    //Log
    private LocalDate dataCadastro;
    private LocalDate dataUltimaEdicao;

    @Override
    public String toString() {
        return nome;
    }

    public String getAbsoluteUrl() {
        return String.format("/musica/%s/", this.slug);
    }

    public String getLetraInicio() {
        String retorno = this.letra.substring(0, this.letra.length() < 140 ? this.letra.length() : 140)
                .replace("<strong>", "").replace("<strong", "").replace("<stron", "").replace("<stro", "").replace("<str", "").replace("<st", "").replace("<s", "")
                .replace("</strong>", "").replace("</strong", "").replace("</stron", "").replace("</stro", "").replace("</str", "").replace("</st", "").replace("</s", "")
                .replace("</", "").replace("<", "")+"...";
        return retorno.trim();
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
