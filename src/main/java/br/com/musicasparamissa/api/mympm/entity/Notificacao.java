package br.com.musicasparamissa.api.mympm.entity;

import br.com.musicasparamissa.api.util.DateTimeJsonDeserializer;
import br.com.musicasparamissa.api.util.DateTimeJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "mympm_notificacoes")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String titulo;
    private String texto;
    private String link;
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date data;
    private Boolean lida;
    @ManyToOne
    @JoinColumn(name = "id_destinatario", referencedColumnName = "id")
    private Usuario destinatario;
    @ManyToOne
    @JoinColumn(name = "id_remetente", referencedColumnName = "id")
    private Usuario remetente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notificacao that = (Notificacao) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(titulo, that.titulo) &&
                Objects.equals(texto, that.texto) &&
                Objects.equals(link, that.link) &&
                Objects.equals(data, that.data) &&
                Objects.equals(lida, that.lida) &&
                Objects.equals(destinatario, that.destinatario) &&
                Objects.equals(remetente, that.remetente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, texto, link, data, lida, destinatario, remetente);
    }
}
