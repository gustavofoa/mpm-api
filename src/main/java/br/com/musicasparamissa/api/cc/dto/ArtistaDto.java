package br.com.musicasparamissa.api.cc.dto;

import br.com.musicasparamissa.api.cc.entity.Artista;
import lombok.Data;

@Data
public class ArtistaDto {

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
