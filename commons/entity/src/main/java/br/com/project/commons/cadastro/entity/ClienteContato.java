package br.com.project.commons.cadastro.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * ClienteContato entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CLIENTE_CONTATO")
public class ClienteContato extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@TextFilter(label="lbl_nome", icon="fa fa-share-alt", length = 50)
	private String nome;
	@TextFilter(label="lbl_cargo", icon="fa fa-share-alt", length = 50)
	private String cargo;
	@TextFilter(label="lbl_email", icon="fa fa-share-alt", length = 50)
	private String email;
	private Cliente cliente;
	private String telefone;
	private Integer ramal;
	private String celular;
	private Date dataAniversario;
	
	@Id
	@Column(name = "ID_CLIENTE_CONTATO", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_CLIENTE_CONTATO")
	@SequenceGenerator(name = "SEQ_CLIENTE_CONTATO", sequenceName = "SEQ_CLIENTE_CONTATO")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CLIENTE", nullable = false)
	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Column(name = "NOME", length = 50)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "CARGO", length = 50)
	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Column(name = "TELEFONE", length = 15)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "RAMAL", precision = 5)
	public Integer getRamal() {
		return this.ramal;
	}

	public void setRamal(Integer ramal) {
		this.ramal = ramal;
	}

	@Column(name = "CELULAR", length = 15)
	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_ANIVERSARIO")
	public Date getDataAniversario() {
		return this.dataAniversario;
	}

	public void setDataAniversario(Date dataAniversario) {
		this.dataAniversario = dataAniversario;
	}
}