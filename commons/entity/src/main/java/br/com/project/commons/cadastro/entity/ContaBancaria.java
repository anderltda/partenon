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

import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * ContaBancaria entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CONTA_BANCARIA", uniqueConstraints = @UniqueConstraint(columnNames = { "CONTA", "ID_AGENCIA", "DIGITO" }))
public class ContaBancaria extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Agencia agencia;
	private String conta;
	private String digito;
	private String nomeTitular;
	private String cpfTitular;
	
	@Id
	@Column(name = "ID_CONTA_BANCARIA", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_CONTA_BANCARIA")
	@SequenceGenerator(name = "SEQ_CONTA_BANCARIA", sequenceName = "SEQ_CONTA_BANCARIA")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AGENCIA", nullable = false)
	public Agencia getAgencia() {
		return this.agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	@Column(name = "CONTA", nullable = false, length = 20)
	public String getConta() {
		return this.conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	@Column(name = "DIGITO", nullable = false, length = 2)
	public String getDigito() {
		return this.digito;
	}

	public void setDigito(String digito) {
		this.digito = digito;
	}

	@Column(name = "NOME_TITULAR", length = 100)
	public String getNomeTitular() {
		return nomeTitular;
	}

	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}

	@Column(name = "CPF_TITULAR", length = 18)
	public String getCpfTitular() {
		return cpfTitular;
	}

	public void setCpfTitular(String cpfTitular) {
		this.cpfTitular = cpfTitular;
	}
}