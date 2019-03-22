package br.com.project.commons.cadastro.entity;

import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import br.com.project.commons.annotation.filter.MaskFilter;
import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * Filial entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FILIAL", uniqueConstraints = { @UniqueConstraint(columnNames = "CNPJ"), @UniqueConstraint(columnNames = "SIGLA") })
public class Filial extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;
	
	public static final String CATEGORIA_MATRIZ = "M";
	public static final String CATEGORIA_FILIAL = "F";
	public static final String CATEGORIA_PARCEIRO = "P";
	public static final String CATEGORIA_VIRTUAL = "V";

	private Long id;
	private EnderecoDominio endereco;
		
	@TextFilter(label="lbl_razao_social", icon="fa fa-share-alt", length = 200)
	private String razaoSocial;

	@MaskFilter(label="lbl_cnpj", mask="99.999.999/9999-99", icon="fa fa-share-alt", length = 18)
	private String cnpj;		
	
	@TextFilter(label="lbl_nome_fantasia", icon="fa fa-share-alt",  length = 200)
	private String nomeFantasia;

	@TextFilter(label="lbl_sigla", style="width:70px;", styleClass="field-uppercase", icon="fa fa-share-alt", length = 3)
	private String sigla;
	
	private String categoria;
	private String inscricaoMunicipal;
	private String inscricaoEstadual;
	private String responsavel;
	private String telefone1;
	private String telefone2;
	private String certificadoCte;
	private String ambienteCte;
	private String localXmlCte;
	private String localSchemaCte;
	private String localDacteCte;
	private String certificadoNfe;
	private String ambienteNfe;
	private String localXmlNfe;
	private String localSchemaNfe;
	private String localDanfeNfe;
	private String usuario;
	private String password;
	private String email;
	private String pop;
	private String smtp;
	private String autenticacao;
	private Boolean autentica;
	private Integer copiasCte;
	private Integer copiasNfe;
	private Integer portaPop;
	private Integer portaSmtp;
	private byte[] imagem;
	private Date dataValidadeCte;
	private Date dataValidadeNfe;
	
	
	@Id
	@Column(name = "ID_FILIAL", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_FILIAL")
	@SequenceGenerator(name = "SEQ_FILIAL", sequenceName = "SEQ_FILIAL")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ENDERECO_DOMINIO")
	public EnderecoDominio getEndereco() {
		return this.endereco;
	}

	public void setEndereco(EnderecoDominio endereco) {
		this.endereco = endereco;
	}

	@Column(name = "SIGLA", unique = true, nullable = false, length = 3)
	public String getSigla() {
		return this.sigla != null ? this.sigla.toUpperCase() : this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Column(name = "CNPJ", unique = true, length = 18)
	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Column(name = "CATEGORIA", nullable = false, length = 20)
	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Column(name = "INSCRICAO_MUNICIPAL", length = 30)
	public String getInscricaoMunicipal() {
		return this.inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	@Column(name = "INSCRICAO_ESTADUAL", length = 30)
	public String getInscricaoEstadual() {
		return this.inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	@Column(name = "RESPONSAVEL", length = 100)
	public String getResponsavel() {
		return this.responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	@Column(name = "RAZAO_SOCIAL", nullable = false, length = 200)
	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Column(name = "NOME_FANTASIA", nullable = false, length = 200)
	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	@Column(name = "TELEFONE1", length = 20)
	public String getTelefone1() {
		return this.telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	@Column(name = "TELEFONE2", length = 20)
	public String getTelefone2() {
		return this.telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	@Column(name = "IMAGEM")
	public byte[] getImagem() {
		return imagem;
	}
	
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	@Column(name = "CERTIFICADO_CTE", length = 100)
	public String getCertificadoCte() {
		return this.certificadoCte;
	}

	public void setCertificadoCte(String certificadoCte) {
		this.certificadoCte = certificadoCte;
	}

	@Column(name = "AMBIENTE_CTE", length = 20)
	public String getAmbienteCte() {
		return this.ambienteCte;
	}

	public void setAmbienteCte(String ambienteCte) {
		this.ambienteCte = ambienteCte;
	}

	@Column(name = "COPIAS_CTE", precision = 0)
	public Integer getCopiasCte() {
		return this.copiasCte;
	}

	public void setCopiasCte(Integer copiasCte) {
		this.copiasCte = copiasCte;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_VALIDADE_CTE")
	public Date getDataValidadeCte() {
		return this.dataValidadeCte;
	}

	public void setDataValidadeCte(Date dataValidadeCte) {
		this.dataValidadeCte = dataValidadeCte;
	}

	@Column(name = "LOCAL_XML_CTE", length = 200)
	public String getLocalXmlCte() {
		return this.localXmlCte;
	}

	public void setLocalXmlCte(String localXmlCte) {
		this.localXmlCte = localXmlCte;
	}

	@Column(name = "LOCAL_SCHEMA_CTE", length = 200)
	public String getLocalSchemaCte() {
		return this.localSchemaCte;
	}

	public void setLocalSchemaCte(String localSchemaCte) {
		this.localSchemaCte = localSchemaCte;
	}

	@Column(name = "LOCAL_DACTE_CTE", length = 200)
	public String getLocalDacteCte() {
		return this.localDacteCte;
	}

	public void setLocalDacteCte(String localDacteCte) {
		this.localDacteCte = localDacteCte;
	}

	@Column(name = "CERTIFICADO_NFE", length = 100)
	public String getCertificadoNfe() {
		return this.certificadoNfe;
	}

	public void setCertificadoNfe(String certificadoNfe) {
		this.certificadoNfe = certificadoNfe;
	}

	@Column(name = "AMBIENTE_NFE", length = 20)
	public String getAmbienteNfe() {
		return this.ambienteNfe;
	}

	public void setAmbienteNfe(String ambienteNfe) {
		this.ambienteNfe = ambienteNfe;
	}

	@Column(name = "COPIAS_NFE", precision = 0)
	public Integer getCopiasNfe() {
		return this.copiasNfe;
	}

	public void setCopiasNfe(Integer copiasNfe) {
		this.copiasNfe = copiasNfe;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_VALIDADE_NFE")
	public Date getDataValidadeNfe() {
		return this.dataValidadeNfe;
	}

	public void setDataValidadeNfe(Date dataValidadeNfe) {
		this.dataValidadeNfe = dataValidadeNfe;
	}

	@Column(name = "LOCAL_XML_NFE", length = 200)
	public String getLocalXmlNfe() {
		return this.localXmlNfe;
	}

	public void setLocalXmlNfe(String localXmlNfe) {
		this.localXmlNfe = localXmlNfe;
	}

	@Column(name = "LOCAL_SCHEMA_NFE", length = 200)
	public String getLocalSchemaNfe() {
		return this.localSchemaNfe;
	}

	public void setLocalSchemaNfe(String localSchemaNfe) {
		this.localSchemaNfe = localSchemaNfe;
	}

	@Column(name = "LOCAL_DANFE_NFE", length = 200)
	public String getLocalDanfeNfe() {
		return this.localDanfeNfe;
	}

	public void setLocalDanfeNfe(String localDanfeNfe) {
		this.localDanfeNfe = localDanfeNfe;
	}

	@Column(name = "USUARIO", length = 50)
	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name = "PASSWORD", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "EMAIL", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "POP", length = 100)
	public String getPop() {
		return this.pop;
	}

	public void setPop(String pop) {
		this.pop = pop;
	}

	@Column(name = "SMTP", length = 100)
	public String getSmtp() {
		return this.smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	@Column(name = "PORTA_POP", precision = 0)
	public Integer getPortaPop() {
		return this.portaPop;
	}

	public void setPortaPop(Integer portaPop) {
		this.portaPop = portaPop;
	}

	@Column(name = "PORTA_SMTP", precision = 0)
	public Integer getPortaSmtp() {
		return this.portaSmtp;
	}

	public void setPortaSmtp(Integer portaSmtp) {
		this.portaSmtp = portaSmtp;
	}

	@Column(name = "AUTENTICACAO", length = 1)
	public String getAutenticacao() {
		return this.autenticacao;
	}

	public void setAutenticacao(String autenticacao) {
		this.autenticacao = autenticacao;
	}
	
	@Transient
	public Boolean getAutentica() {		
		return autentica;
	}

	public void setAutentica(Boolean autentica) {
		this.autentica = autentica;
	}
}