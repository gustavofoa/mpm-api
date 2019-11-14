package br.com.musicasparamissa.api.mympm.entity;

import br.com.musicasparamissa.api.util.DateTimeJsonDeserializer;
import br.com.musicasparamissa.api.util.DateTimeJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "mympm_compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_usuario")
    private Long idUsuario;

    private String plano;

    @Column(name = "data_compra")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date dataCompra;
    @Column(name = "data_expiracao")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date dataExpiracao;

    @Column(name = "valor_pago")
    private Double valorPago;

    private String gateway;

}
