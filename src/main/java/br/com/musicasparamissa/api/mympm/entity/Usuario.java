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
@Entity(name = "mympm_usuario")
public class Usuario {

    @Id
    private Long id;
    private String email;
    private String senha;
    private String nome;
    private String imagem;
    private String gateway;

    private String cpf;

    @Column(name = "dt_compra")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date dataCompra;
    @Column(name = "dt_expiracao")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date dataExpiracao;

    private Double valorPago;
    private String plano;

    private String facebook;
    private String twitter;
    private String instagram;
    private Boolean premium;

    @Override
    public String toString() {
        return email + " - " + nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id) &&
                email.equals(usuario.email) &&
                Objects.equals(senha, usuario.senha) &&
                Objects.equals(nome, usuario.nome) &&
                Objects.equals(imagem, usuario.imagem) &&
                cpf.equals(usuario.cpf) &&
                Objects.equals(dataCompra, usuario.dataCompra) &&
                Objects.equals(dataExpiracao, usuario.dataExpiracao) &&
                Objects.equals(valorPago, usuario.valorPago) &&
                Objects.equals(plano, usuario.plano) &&
                Objects.equals(facebook, usuario.facebook) &&
                Objects.equals(twitter, usuario.twitter) &&
                Objects.equals(instagram, usuario.instagram);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, senha, nome, imagem, cpf, dataCompra, dataExpiracao, valorPago, plano, facebook, twitter, instagram);
    }

}
