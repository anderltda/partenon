package br.com.project.commons.cadastro.entity;

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
import javax.persistence.UniqueConstraint;

import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * Cliente entity. 
 * @author MyEclipse Persistence Tools
 * 
 */
@Entity
@Table(name = "CLIENTE", uniqueConstraints = @UniqueConstraint(columnNames = "CNPJ"))
public class Cliente extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private EnderecoDominio endereco;
	private Filial filialResponsavel;
	private Filial filialCobranca;
	private TipoContribuinte tipoContribuinte;
	
	@TextFilter(label="lbl_cpf_cnpj", onkeyup="util.formatCnpjCpf(this);", icon="fa fa-share-alt", length = 18)
	private String cnpj;
	
	@TextFilter(label="lbl_razao_social", icon="fa fa-share-alt", length = 200)
	private String razaoSocial;
	
	@TextFilter(label="lbl_nome_fantasia", icon="fa fa-share-alt",  length = 200)
	private String nomeFantasia;
	
	private String inscricaoEstadual;
	private String inscricaoMunicipal;
	private String inscricaoSuframa;
	private String telefone1;
	private String telefone2;
	private String serieNf;
	private String incentivo;
	private Double incentivoPercentual;
	private String incentivoArtigo;
	private String status;	
	
	@Id
	@Column(name = "ID_CLIENTE", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_CLIENTE")
	@SequenceGenerator(name = "SEQ_CLIENTE", sequenceName = "SEQ_CLIENTE")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FILIAL_RESPONSAVEL")
	public Filial getFilialResponsavel() {
		return filialResponsavel;
	}
	
	public void setFilialResponsavel(Filial filialResponsavel) {
		this.filialResponsavel = filialResponsavel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FILIAL_COBRANCA")
	public Filial getFilialCobranca() {
		return filialCobranca;
	}
	
	public void setFilialCobranca(Filial filialCobranca) {
		this.filialCobranca = filialCobranca;
	}

	@Column(name = "CNPJ", unique = true, length = 22)
	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Column(name = "RAZAO_SOCIAL", length = 100)
	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Column(name = "NOME_FANTASIA", length = 100)
	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_CONTRIBUINTE")
	public TipoContribuinte getTipoContribuinte() {
		return this.tipoContribuinte;
	}

	public void setTipoContribuinte(TipoContribuinte tipoContribuinte) {
		this.tipoContribuinte = tipoContribuinte;
	}

	@Column(name = "INSCRICAO_ESTADUAL", nullable = false, length = 50)
	public String getInscricaoEstadual() {
		return this.inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	@Column(name = "INSCRICAO_MUNICIPAL", nullable = false, length = 50)
	public String getInscricaoMunicipal() {
		return this.inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	@Column(name = "INSCRICAO_SUFRAMA", nullable = false, length = 50)
	public String getInscricaoSuframa() {
		return this.inscricaoSuframa;
	}

	public void setInscricaoSuframa(String inscricaoSuframa) {
		this.inscricaoSuframa = inscricaoSuframa;
	}

	@Column(name = "TELEFONE_1", nullable = false, length = 15)
	public String getTelefone1() {
		return this.telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	@Column(name = "TELEFONE_2", nullable = false, length = 15)
	public String getTelefone2() {
		return this.telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	@Column(name = "SERIE_NF", length = 3)
	public String getSerieNf() {
		return this.serieNf;
	}

	public void setSerieNf(String serieNf) {
		this.serieNf = serieNf;
	}

	@Column(name = "INCENTIVO", length = 1)
	public String getIncentivo() {
		return this.incentivo;
	}

	public void setIncentivo(String incentivo) {
		this.incentivo = incentivo;
	}

	@Column(name = "INCENTIVO_PERCENTUAL", nullable = false, precision = 0)
	public Double getIncentivoPercentual() {
		return this.incentivoPercentual;
	}

	public void setIncentivoPercentual(Double incentivoPercentual) {
		this.incentivoPercentual = incentivoPercentual;
	}

	@Column(name = "INCENTIVO_ARTIGO", nullable = false, length = 100)
	public String getIncentivoArtigo() {
		return this.incentivoArtigo;
	}

	public void setIncentivoArtigo(String incentivoArtigo) {
		this.incentivoArtigo = incentivoArtigo;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}