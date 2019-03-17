package br.com.musicasparamissa.api.mympm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "mympm_usuario")
public class Usuario {

    @Id
    private Integer id;
    private String email;
    private String senha;
    private String nome;
    private String imagem;

    private String cpf;

    @Column(name = "dt_compra")
    private LocalDate dataCompra;
    @Column(name = "dt_expiracao")
    private LocalDate dataExpiracao;

    private Double valorPago;
    private String plano;

    private String facebook;
    private String twitter;
    private String instagram;

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
