package br.com.project.commons.cadastro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * TipoCobranca entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TIPO_COBRANCA", uniqueConstraints = @UniqueConstraint(columnNames = "NOME"))
public class TipoCobranca extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String descricao;
	private String tipo;	
	
	@Id
	@Column(name = "ID_TIPO_COBRANCA", unique = true, nullable = false, precision = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NOME", unique = true, nullable = false, length = 100)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "DESCRICAO", nullable = false, length = 100)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "TIPO", nullable = false, length = 3)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}