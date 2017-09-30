package br.com.musicasparamissa.mpmjadmin.backend.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;

@Getter
@Setter
@Entity(name = "mpm_itemliturgia")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "_class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SugestaoMusica.class),
        @JsonSubTypes.Type(value = Leitura.class)
})
public abstract class ItemLiturgia implements Comparable<ItemLiturgia> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "dialiturgico_id", referencedColumnName = "slug")
    private DiaLiturgico diaLiturgico;
    private Integer posicao;

    @Override
    public String toString() {
        String tipo = this instanceof Leitura ? "L - " : this instanceof SugestaoMusica ? "M - " : "? - ";
        return posicao + " - " + tipo + titulo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        ItemLiturgia other = (ItemLiturgia) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public ItemLiturgia copy(DiaLiturgico diaLiturgico) {
        ItemLiturgia item = null;
        if (this instanceof Leitura) {
            item = new Leitura();
            ((Leitura) item).setMarcacaoBiblia(((Leitura) this).getMarcacaoBiblia());
            ((Leitura) item).setTexto(((Leitura) this).getTexto());
        } else if (this instanceof SugestaoMusica) {
            item = new SugestaoMusica();
            ((SugestaoMusica) item).setCategorias(new HashSet<>());
            ((SugestaoMusica) item).getCategorias().addAll(((SugestaoMusica) this).getCategorias());
            ((SugestaoMusica) item).setAvulsas(new HashSet<>());
            ((SugestaoMusica) item).getAvulsas().addAll(((SugestaoMusica) this).getAvulsas());
            ((SugestaoMusica) item).setRemover(new HashSet<>());
            ((SugestaoMusica) item).getRemover().addAll(((SugestaoMusica) this).getRemover());
        }
        if (item == null) {
            return null;
        }
        item.setTitulo(this.getTitulo());
        item.setDescricao(this.getDescricao());
        item.setPosicao(this.getPosicao());
        item.setDiaLiturgico(diaLiturgico);
        return item;
    }

    public int compareTo(ItemLiturgia item) {
        return this.posicao - item.posicao;
    }

}
