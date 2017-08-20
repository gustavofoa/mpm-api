package br.com.musicasparamissa.mpmjadmin.backend.entity;

import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name="mpm_musica")
public class Musica {

	@Id
	private String slug;
	private String nome;
	private String letra;
	private String cifra;
	private String info;
	@Column(name="link_video")
	private String linkVideo;

	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="mpm_musica_categorias", joinColumns={@JoinColumn(name="musica_id")}, inverseJoinColumns={@JoinColumn(name="categoria_id")})
	private Set<Categoria> categorias;
	private Float rating;
	private Integer votes;
	@Column(name="link_lpsalmo")
	private String linkLpSalmo;
	
	@Override
	public String toString() {
		return nome;
	}
	
	public String getLetraInicio(){
		if(letra == null){
			return "";
		}
		final int tamanho = 100;
		if(letra.trim().length() < tamanho){
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

}
