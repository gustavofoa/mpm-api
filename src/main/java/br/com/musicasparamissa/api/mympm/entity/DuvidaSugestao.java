package br.com.musicasparamissa.api.mympm.entity;

import br.com.musicasparamissa.api.util.DateTimeJsonDeserializer;
import br.com.musicasparamissa.api.util.DateTimeJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "mympm_duvidas_sugestoes")
public class DuvidaSugestao {

    @Id
    private Integer id;
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date data;
    private String tipo;
    private String status;
    private String titulo;
    private String texto;
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;


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
                Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, tipo, status, titulo, texto, usuario);
    }
}
