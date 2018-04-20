package br.com.musicasparamissa.api.mpm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "mpm_sugestaomusica")
@PrimaryKeyJoinColumn(name = "itemliturgia_ptr_id")
public class SugestaoMusica extends ItemLiturgia {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "mpm_sugestaomusica_categorias", joinColumns = {@JoinColumn(name = "sugestaomusica_id")}, inverseJoinColumns = {@JoinColumn(name = "categoria_id")})
    private Set<Categoria> categorias;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "mpm_sugestaomusica_avulsas", joinColumns = {@JoinColumn(name = "sugestaomusica_id")}, inverseJoinColumns = {@JoinColumn(name = "musica_id")})
    private Set<Musica> avulsas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "mpm_sugestaomusica_remover", joinColumns = {@JoinColumn(name = "sugestaomusica_id")}, inverseJoinColumns = {@JoinColumn(name = "musica_id")})
    private Set<Musica> remover;

    public List<Musica> getMusicas() {
        List<Musica> musicas = new ArrayList<>();
        for (Categoria categoria : categorias)
            for (Musica musica : categoria.getMusicas())
                musicas.add(musica);

        for (Musica avulsa : this.avulsas)
            musicas.add(avulsa);

        for (Musica aRemover : this.remover)
            if (musicas.contains(aRemover))
                musicas.remove(aRemover);

        Collections.shuffle(musicas);

        return musicas;
    }

}
