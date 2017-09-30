package br.com.musicasparamissa.mpmjadmin.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "mpm_categoria")
public class Categoria implements Comparable<Categoria> {

    @Id
    private String slug;
    private String nome;
    private String descricao;
    private Integer ordem;
    @Column(name = "categoria_mae_id")
    private String categoriaMae;

    @OneToMany(mappedBy = "categoriaMae", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Categoria> children;

    @Override
    public String toString() {
        return slug;
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
        Categoria other = (Categoria) obj;
        if (slug == null) {
            if (other.slug != null)
                return false;
        } else if (!slug.equals(other.slug))
            return false;
        return true;
    }

    @Override
    public int compareTo(Categoria o) {
        return ordem - o.getOrdem();
    }
}
