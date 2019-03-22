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
import javax.persistence.UniqueConstraint;

import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * Motorista entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MOTORISTA", uniqueConstraints = @UniqueConstraint(columnNames = "CPF"))
public class Motorista extends BasicLoggableEntityObject<Long> {
	
	private static final long serialVersionUID = 1L;
	
	public static final String CATEGORIA_PROPRIETARIO = "P";
	public static final String CATEGORIA_MOTORISTA = "M";
	public static final String CATEGORIA_AJUDANTE = "A";

	private Long id;
	private ContaBancaria contaBancaria;
	private EnderecoDominio endereco;
	
	@TextFilter(label="lbl_cpf_cnpj", onkeyup="util.formatCnpjCpf(this);", icon="fa fa-share-alt", length = 18)
	private String cpf;
		
	@TextFilter(label="lbl_nome", icon="fa fa-share-alt", length = 100)
	private String nome;	
	
	@TextFilter(label="lbl_cnh", icon="fa fa-share-alt", length = 20)
	private String numeroCnh;

	@TextFilter(label="lbl_rg", icon="fa fa-share-alt", length = 20)
	private String rg;

	private String categoria;
	private String telefone;
	private String celular;
	private String idNextel;
	private String titularConta;
	private String cidadeNascimento;
	private String ufNascimento;
	private String categoriaCnh;
	private String crntrcAntt;
	private String mopp;
	private String rgEmissor;
	private String vitimaAssalto;
	private String sofreuAcidente;
	private String recolheIr;
	private String recolheInss;
	private String recolheSestSenat;
	private byte[] imagem;
	private Date dataNascimento;
	private Date dataCnhEmissao;
	private Date dataCnhValidade;
	private Date dataLiberacaoSeguradora;
	private Date dataLiberacaoValidade;
	private Date dataCrntrcAnttValidade;
	private Date dataMoppValidade;
	private Date dataRgEmissao;
	
	@Id
	@Column(name = "ID_MOTORISTA", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_MOTORISTA")
	@SequenceGenerator(name = "SEQ_MOTORISTA", sequenceName = "SEQ_MOTORISTA")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTA_BANCARIA")
	public ContaBancaria getContaBancaria() {
		return this.contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	@ManyToOne(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ENDERECO_DOMINIO")
	public EnderecoDominio getEndereco() {
		return this.endereco;
	}

	public void setEndereco(EnderecoDominio endereco) {
		this.endereco = endereco;
	}

	@Column(name = "CPF", unique = true, nullable = false, length = 18)
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "CATEGORIA", nullable = false, length = 20)
	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Column(name = "NOME", nullable = false, length = 100)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "TELEFONE", length = 20)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "CELULAR", length = 20)
	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Column(name = "ID_NEXTEL", length = 20)
	public String getIdNextel() {
		return this.idNextel;
	}

	public void setIdNextel(String idNextel) {
		this.idNextel = idNextel;
	}

	@Column(name = "TITULAR_CONTA", length = 100)
	public String getTitularConta() {
		return this.titularConta;
	}

	public void setTitularConta(String titularConta) {
		this.titularConta = titularConta;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_NASCIMENTO")
	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Column(name = "CIDADE_NASCIMENTO", length = 50)
	public String getCidadeNascimento() {
		return this.cidadeNascimento;
	}

	public void setCidadeNascimento(String cidadeNascimento) {
		this.cidadeNascimento = cidadeNascimento;
	}

	@Column(name = "UF_NASCIMENTO", length = 2)
	public String getUfNascimento() {
		return this.ufNascimento;
	}

	public void setUfNascimento(String ufNascimento) {
		this.ufNascimento = ufNascimento;
	}

	@Column(name = "NUMERO_CNH", length = 20)
	public String getNumeroCnh() {
		return this.numeroCnh;
	}

	public void setNumeroCnh(String numeroCnh) {
		this.numeroCnh = numeroCnh;
	}

	@Column(name = "CATEGORIA_CNH", length = 10)
	public String getCategoriaCnh() {
		return this.categoriaCnh;
	}

	public void setCategoriaCnh(String categoriaCnh) {
		this.categoriaCnh = categoriaCnh;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_CNH_EMISSAO")
	public Date getDataCnhEmissao() {
		return this.dataCnhEmissao;
	}

	public void setDataCnhEmissao(Date dataCnhEmissao) {
		this.dataCnhEmissao = dataCnhEmissao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_CNH_VALIDADE")
	public Date getDataCnhValidade() {
		return this.dataCnhValidade;
	}

	public void setDataCnhValidade(Date dataCnhValidade) {
		this.dataCnhValidade = dataCnhValidade;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_LIBERACAO_SEGURADORA")
	public Date getDataLiberacaoSeguradora() {
		return this.dataLiberacaoSeguradora;
	}

	public void setDataLiberacaoSeguradora(Date dataLiberacaoSeguradora) {
		this.dataLiberacaoSeguradora = dataLiberacaoSeguradora;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_LIBERACAO_VALIDADE")
	public Date getDataLiberacaoValidade() {
		return this.dataLiberacaoValidade;
	}

	public void setDataLiberacaoValidade(Date dataLiberacaoValidade) {
		this.dataLiberacaoValidade = dataLiberacaoValidade;
	}

	@Column(name = "CRNTRC_ANTT", length = 30)
	public String getCrntrcAntt() {
		return this.crntrcAntt;
	}

	public void setCrntrcAntt(String crntrcAntt) {
		this.crntrcAntt = crntrcAntt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_CRNTRC_ANTT_VALIDADE")
	public Date getDataCrntrcAnttValidade() {
		return this.dataCrntrcAnttValidade;
	}

	public void setDataCrntrcAnttValidade(Date dataCrntrcAnttValidade) {
		this.dataCrntrcAnttValidade = dataCrntrcAnttValidade;
	}

	@Column(name = "MOPP", length = 30)
	public String getMopp() {
		return this.mopp;
	}

	public void setMopp(String mopp) {
		this.mopp = mopp;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_MOPP_VALIDADE")
	public Date getDataMoppValidade() {
		return this.dataMoppValidade;
	}

	public void setDataMoppValidade(Date dataMoppValidade) {
		this.dataMoppValidade = dataMoppValidade;
	}

	@Column(name = "RG", length = 15)
	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_RG_EMISSAO")
	public Date getDataRgEmissao() {
		return this.dataRgEmissao;
	}

	public void setDataRgEmissao(Date dataRgEmissao) {
		this.dataRgEmissao = dataRgEmissao;
	}

	@Column(name = "RG_EMISSOR", length = 10)
	public String getRgEmissor() {
		return this.rgEmissor;
	}

	public void setRgEmissor(String rgEmissor) {
		this.rgEmissor = rgEmissor;
	}

	@Column(name = "VITIMA_ASSALTO", length = 1)
	public String getVitimaAssalto() {
		return this.vitimaAssalto;
	}

	public void setVitimaAssalto(String vitimaAssalto) {
		this.vitimaAssalto = vitimaAssalto;
	}

	@Column(name = "SOFREU_ACIDENTE", length = 1)
	public String getSofreuAcidente() {
		return this.sofreuAcidente;
	}

	public void setSofreuAcidente(String sofreuAcidente) {
		this.sofreuAcidente = sofreuAcidente;
	}

	@Column(name = "RECOLHE_IR", length = 1)
	public String getRecolheIr() {
		return this.recolheIr;
	}

	public void setRecolheIr(String recolheIr) {
		this.recolheIr = recolheIr;
	}

	@Column(name = "RECOLHE_INSS", length = 1)
	public String getRecolheInss() {
		return this.recolheInss;
	}

	public void setRecolheInss(String recolheInss) {
		this.recolheInss = recolheInss;
	}

	@Column(name = "RECOLHE_SEST_SENAT", length = 1)
	public String getRecolheSestSenat() {
		return this.recolheSestSenat;
	}

	public void setRecolheSestSenat(String recolheSestSenat) {
		this.recolheSestSenat = recolheSestSenat;
	}

	@Column(name = "IMAGEM")
	public byte[] getImagem() {
		return imagem;
	}
	
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
}