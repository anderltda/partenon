package br.com.project.commons.cadastro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.project.commons.annotation.filter.SelectFilter;
import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * Agencia entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AGENCIA", uniqueConstraints = @UniqueConstraint(columnNames = { "ID_BANCO", "NUMERO", "DIGITO" }))
public class Agencia extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@SelectFilter(label="lbl_banco", method="#{agenciaView.getBancos()}", exclusiveDialog = true)
	private Banco banco;
	
	@TextFilter(label="lbl_numero_agencia", icon="fa fa-share-alt", length = 100)
	private String numero;
	
	@TextFilter(label="lbl_cidade", length = 100)
	private String cidade;
	
	@SelectFilter(label="lbl_estado", method="#{agenciaView.getEstados()}")
	private String uf;
	
	private String digito;
	private String nome;
	
	@Id
	@Column(name = "ID_AGENCIA", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_AGENCIA")
	@SequenceGenerator(name = "SEQ_AGENCIA", sequenceName = "SEQ_AGENCIA")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BANCO", nullable = false)
	public Banco getBanco() {
		return this.banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	@Column(name = "NUMERO", nullable = false, length = 10)
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name = "DIGITO", nullable = false, length = 2)
	public String getDigito() {
		return this.digito;
	}

	public void setDigito(String digito) {
		this.digito = digito;
	}

	@Column(name = "NOME", length = 100)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "CIDADE", nullable = false, length = 50)
	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Column(name = "UF", nullable = false, length = 2)
	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}