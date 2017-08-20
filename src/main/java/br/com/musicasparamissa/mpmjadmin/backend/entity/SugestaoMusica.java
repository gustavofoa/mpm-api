package br.com.musicasparamissa.mpmjadmin.backend.entity;

import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name="mpm_sugestaomusica")
@PrimaryKeyJoinColumn(name="itemliturgia_ptr_id")
public class SugestaoMusica extends ItemLiturgia {
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="mpm_sugestaomusica_categorias", joinColumns={@JoinColumn(name="sugestaomusica_id")}, inverseJoinColumns={@JoinColumn(name="categoria_id")})
    private Set<Categoria> categorias;

	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="mpm_sugestaomusica_avulsas", joinColumns={@JoinColumn(name="sugestaomusica_id")}, inverseJoinColumns={@JoinColumn(name="musica_id")})
	private Set<Musica> avulsas;

	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="mpm_sugestaomusica_remover", joinColumns={@JoinColumn(name="sugestaomusica_id")}, inverseJoinColumns={@JoinColumn(name="musica_id")})
	private Set<Musica> remover;

	public void joinCategorias(ObservableList<Categoria> items) {
		if(categorias == null){
			categorias = new HashSet<>();
			categorias.addAll(items);
		} else {
			//remove
			final Set<Categoria> removeds = new HashSet<>();
			for(Categoria categoria : categorias){
				if(!items.contains(categoria)){
					removeds.add(categoria);
				}
			}
			categorias.removeAll(removeds);
			//add
			categorias.addAll(items);
		}
	}

	public void joinAvulsas(ObservableList<Musica> items) {
		if(avulsas == null){
			avulsas = new HashSet<>();
			avulsas.addAll(items);
		} else {
			//remove
			final Set<Musica> removeds = new HashSet<>();
			for(Musica musica : avulsas){
				if(!items.contains(musica)){
					removeds.add(musica);
				}
			}
			avulsas.removeAll(removeds);
			//add
			avulsas.addAll(items);
		}
	}

	public void joinRemover(ObservableList<Musica> items) {
		if(remover == null){
			remover = new HashSet<>();
			remover.addAll(items);
		} else {
			//remove
			final Set<Musica> removeds = new HashSet<>();
			for(Musica musica : remover){
				if(!items.contains(musica)){
					removeds.add(musica);
				}
			}
			remover.removeAll(removeds);
			//add
			remover.addAll(items);
		}
	}
	
}
