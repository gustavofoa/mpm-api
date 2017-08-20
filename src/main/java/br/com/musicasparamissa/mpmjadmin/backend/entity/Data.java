package br.com.musicasparamissa.mpmjadmin.backend.entity;

import br.com.musicasparamissa.mpmjadmin.backend.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity(name="mpm_data")
public class Data implements Serializable {

	private static final long serialVersionUID = -7033097463967043822L;

	@Id
	@Temporal(value=TemporalType.DATE)
	private Date data;

	private Boolean destaque;
	
	@ManyToOne
	@JoinColumn(name = "liturgia_id", referencedColumnName = "slug")
	private DiaLiturgico liturgia;

	public String getDataFormatada(){
		return DateUtil.format(data);
	}

	public String getDiaLiturgicoSlug(){
		return liturgia.getSlug();
	}

	public String getDiaLiturgicoTitulo(){
		return liturgia.getTitulo();
	}

	public String getDiaLiturgicoIntroducao(){
		return liturgia.getIntroducao();
	}
	
}

