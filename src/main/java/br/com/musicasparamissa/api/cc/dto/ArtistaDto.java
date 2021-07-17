package br.com.musicasparamissa.api.cc.dto;

import br.com.musicasparamissa.api.cc.entity.Artista;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArtistaDto extends RepresentationModel<ArtistaDto> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String slug;
    private String nome;
    private String info;
    private String imagem;

    public ArtistaDto(Artista artista) {
        this.slug = artista.getSlug();
        this.nome = artista.getNome();
        this.info = artista.getInfo();
        this.imagem = artista.getImagem();
    }
}
