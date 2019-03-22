package br.com.project.commons.cadastro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * Banco entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BANCO", uniqueConstraints = @UniqueConstraint(columnNames = "CODIGO"))
public class Banco extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@TextFilter(label="lbl_codigo", icon="fa fa-share-alt", length = 3)
	private String codigo;
	
	@TextFilter(label="lbl_banco", icon="fa fa-share-alt", length = 100)
	private String nome;
	
	@Id
	@Column(name = "ID_BANCO", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_BANCO")
	@SequenceGenerator(name = "SEQ_BANCO", sequenceName = "SEQ_BANCO")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CODIGO", unique = true, nullable = false, length = 3)
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name = "NOME", nullable = false, length = 100)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Transient
	public String getCodigoNome() {
		return this.codigo +" - "+ this.nome;
	}
}