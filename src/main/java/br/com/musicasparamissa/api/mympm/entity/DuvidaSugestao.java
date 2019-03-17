package br.com.musicasparamissa.api.mympm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "mympm_duvidas_sugestoes")
public class DuvidaSugestao {

    @Id
    private Integer id;
    private LocalDate data;
    private DuvidaSugestaoTipo tipo;
    private DuvidaSugestaoStatus status;
    private String titulo;
    private String texto;
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "resposta_a", referencedColumnName = "id")
    DuvidaSugestao respostaA;


    @Override
    public String toString() {
        return data + " - " + usuario.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DuvidaSugestao that = (DuvidaSugestao) o;
        return id.equals(that.id) &&
                data.equals(that.data) &&
                Objects.equals(tipo, that.tipo) &&
                Objects.equals(status, that.status) &&
                Objects.equals(titulo, that.titulo) &&
                Objects.equals(texto, that.texto) &&
                Objects.equals(usuario, that.usuario) &&
                Objects.equals(respostaA, that.respostaA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, tipo, status, titulo, texto, usuario, respostaA);
    }
}
