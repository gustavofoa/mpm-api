package br.com.musicasparamissa.api.mpm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "mpm_dialiturgico")
public class DiaLiturgico implements Serializable {

    private static final long serialVersionUID = 7789330899810340297L;

    @Id
    private String slug;
    private String titulo;
    private String introducao;
    private String img;

    @ManyToOne
    @JoinColumn(name = "banner_footer_id", referencedColumnName = "id")
    private Banner bannerFooter;
    @ManyToOne
    @JoinColumn(name = "banner_lateral_id", referencedColumnName = "id")
    private Banner bannerLateral;

    //Log
    private LocalDate dataCadastro;
    private LocalDate dataUltimaEdicao;

    public String getAbsoluteUrl() {
        return String.format("/sugestoes-para/%s/", this.slug);
    }

    public String getImg80x80url() {
        return String.format("/images/diasLiturgicos/80x80/%s", this.img);
    }

    public String getImgUrl() {
        return String.format("/images/diasLiturgicos/%s", this.img);
    }

    public String getImg300url() {
        return String.format("/images/diasLiturgicos/300/%s", this.img);
    }
}
