package br.com.project.commons.cadastro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.project.commons.annotation.filter.MaskFilter;
import br.com.project.commons.annotation.filter.SelectFilter;
import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.StringUtil;

/**
 * Endereco entity. 
 * @author anderson.nascimento
 *
 */
@Entity
@Table(name = "ENDERECO")
public class Endereco extends BaseObject {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@MaskFilter(label="lbl_cep", style="width:100px;", mask="99999-999", length = 8)
	private String cep;
	
	@TextFilter(label="lbl_logradouro", length = 200)
	private String logradouro;
	
	@TextFilter(label="lbl_bairro", length = 100)
	private String bairro;
	
	@TextFilter(label="lbl_cidade", length = 100)
	private String cidade;
	
	@SelectFilter(label="lbl_estado", method="#{enderecoView.getEstados()}")
	private String uf;
	
	private String resultado;
	private String resultado_txt;
	private String tipo_logradouro;	

	public Endereco() {
		super();
	}

	public Endereco(String cep) {
		this.cep = cep;
	}

	@Id
	@Column(name = "ID_ENDERECO", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_ENDERECO")
	@SequenceGenerator(name = "SEQ_ENDERECO", sequenceName = "SEQ_ENDERECO")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CEP", nullable = false, length = 8)
	public String getCep() {
		return StringUtil.isEmpty(this.cep) ? null : this.cep.replaceAll("[^a-zA-Z0-9]+", "");
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

	@Column(name = "BAIRRO", nullable = false, length = 100)
	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Column(name = "CIDADE", nullable = false, length = 100)
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

	@Transient
	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	@Transient
	public String getResultado_txt() {
		return resultado_txt;
	}

	public void setResultado_txt(String resultado_txt) {
		this.resultado_txt = resultado_txt;
	}

	@Transient
	public String getTipo_logradouro() {
		return tipo_logradouro;
	}

	public void setTipo_logradouro(String tipo_logradouro) {
		this.tipo_logradouro = tipo_logradouro;
	}
	
	@Transient
	public boolean isEnderecoCompleto() {
		return (StringUtil.isNotEmptyTrim(this.cep)
				&& StringUtil.isNotEmptyTrim(this.logradouro)
				&& StringUtil.isNotEmptyTrim(this.bairro)
				&& StringUtil.isNotEmptyTrim(this.cidade) && StringUtil
					.isNotEmptyTrim(this.uf));
	}	
}