package br.com.musicasparamissa.api.mpm.entity;

import br.com.musicasparamissa.api.util.DateJsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity(name = "mpm_data")
public class Data implements Serializable {

    private static final long serialVersionUID = -7033097463967043822L;

    @Id
    @Temporal(value = TemporalType.DATE)
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date data;

    private Boolean destaque;

    @ManyToOne
    @JoinColumn(name = "liturgia_id", referencedColumnName = "slug")
    private DiaLiturgico liturgia;

    //Log
    private LocalDate dataCadastro;
    private LocalDate dataUltimaEdicao;

}

