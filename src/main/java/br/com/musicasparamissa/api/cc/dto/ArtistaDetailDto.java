package br.com.musicasparamissa.api.cc.dto;

import br.com.musicasparamissa.api.cc.entity.Artista;
import br.com.musicasparamissa.api.cc.entity.Musica;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ArtistaDetailDto {

    private String slug;
    private String nome;
    private String info;
    private String imagem;
    private List<MusicaDto> musicas;

    public ArtistaDetailDto(Artista artista, List<Musica> musicas) {
        this.slug = artista.getSlug();
        this.nome = artista.getNome();
        this.info = artista.getInfo();
        this.imagem = artista.getImagem();
        this.musicas = musicas.stream().map(musica -> new MusicaDto(musica)).collect(Collectors.toList());
    }
}
