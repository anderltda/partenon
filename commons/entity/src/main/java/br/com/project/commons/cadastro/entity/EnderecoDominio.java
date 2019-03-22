package br.com.project.commons.cadastro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * EnderecoDominio entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ENDERECO_DOMINIO")
public class EnderecoDominio extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String cep;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String pais;
	private String longitude;
	private String latitude;	

	@Id
	@Column(name = "ID_ENDERECO_DOMINIO", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_ENDERECO_DOMINIO")
	@SequenceGenerator(name = "SEQ_ENDERECO_DOMINIO", sequenceName = "SEQ_ENDERECO_DOMINIO")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CEP", nullable = false, length = 9)
	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(name = "LOGRADOURO", nullable = false, length = 200)
	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Column(name = "NUMERO", nullable = false, precision = 0)
	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Column(name = "COMPLEMENTO", length = 200)
	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Column(name = "BAIRRO", nullable = false, length = 100)
	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
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

	@Column(name = "PAIS", nullable = false, length = 50)
	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Column(name = "LONGITUDE", length = 30)
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "LATITUDE", length = 30)
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}