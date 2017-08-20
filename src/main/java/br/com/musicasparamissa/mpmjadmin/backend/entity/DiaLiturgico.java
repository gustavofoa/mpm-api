package br.com.musicasparamissa.mpmjadmin.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity(name="mpm_dialiturgico")
public class DiaLiturgico implements Serializable {

	private static final long serialVersionUID = 7789330899810340297L;
	
	@Id
	private String slug;
	private String titulo;
	private String introducao;
	private String img;
	
}
