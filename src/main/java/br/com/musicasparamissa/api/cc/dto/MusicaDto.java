package br.com.musicasparamissa.api.cc.dto;

import br.com.musicasparamissa.api.cc.entity.Musica;
import lombok.Data;

@Data
public class MusicaDto {


    private String slug;
    private String nome;
    private String cifra;
    private String info;
    private String video;

    public MusicaDto(Musica musica) {
        this.slug = musica.getSlug();
        this.nome = musica.getNome();
        this.info = musica.getInfo();
        this.cifra = musica.getCifra();
        this.video = musica.getLinkVideo();
    }
}
