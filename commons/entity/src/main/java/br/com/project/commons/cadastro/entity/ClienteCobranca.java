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

import br.com.project.commons.annotation.filter.MaskFilter;
import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * ClienteCobranca entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CLIENTE_COBRANCA")
public class ClienteCobranca extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@MaskFilter(label="lbl_cnpj", mask="99.999.999/9999-99", icon="fa fa-share-alt", length = 18)
	private String cnpj;
	
	@TextFilter(label="lbl_razao_social", icon="fa fa-share-alt", length = 200)
	private String razaoSocial;
	
	private TipoFaturamento tipoFaturamento;
	private ContaBancaria contaBancaria;
	private TipoCobranca tipoCobranca;
	private EnderecoDominio endereco;
	private Cliente cliente;
	private String telefone;
	private String permiteFaturar;
	private String agrupa;
	private String protesta;
	private String temSeguro;
	private String carteira;
	private String taxa;
	private Double limiteFrete;
	private Integer diaVencimento;
	private Integer prazoPagamento;

	@Id
	@Column(name = "ID_CLIENTE_COBRANCA", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_CLIENTE_COBRANCA")
	@SequenceGenerator(name = "SEQ_CLIENTE_COBRANCA", sequenceName = "SEQ_CLIENTE_COBRANCA")
	public Long getId() {
		return this.id;
	}

	public void setId(Long idClienteCobranca) {
		this.id = idClienteCobranca;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_FATURAMENTO", nullable = false)
	public TipoFaturamento getTipoFaturamento() {
		return this.tipoFaturamento;
	}

	public void setTipoFaturamento(TipoFaturamento tipoFaturamento) {
		this.tipoFaturamento = tipoFaturamento;
	}

	@ManyToOne(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTA_BANCARIA", nullable = false)
	public ContaBancaria getContaBancaria() {
		return this.contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_COBRANCA", nullable = false)
	public TipoCobranca getTipoCobranca() {
		return this.tipoCobranca;
	}

	public void setTipoCobranca(TipoCobranca tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	@ManyToOne(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ENDERECO_DOMINIO", nullable = false)
	public EnderecoDominio getEndereco() {
		return this.endereco;
	}

	public void setEndereco(EnderecoDominio endereco) {
		this.endereco = endereco;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CLIENTE", nullable = false)
	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Column(name = "CNPJ", length = 18)
	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Column(name = "RAZAO_SOCIAL", nullable = false, length = 100)
	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Column(name = "TELEFONE", length = 15)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "PERMITE_FATURAR", length = 1)
	public String getPermiteFaturar() {
		return this.permiteFaturar;
	}

	public void setPermiteFaturar(String permiteFaturar) {
		this.permiteFaturar = permiteFaturar;
	}

	@Column(name = "AGRUPA", nullable = false, length = 1)
	public String getAgrupa() {
		return this.agrupa;
	}

	public void setAgrupa(String agrupa) {
		this.agrupa = agrupa;
	}

	@Column(name = "PROTESTA", length = 1)
	public String getProtesta() {
		return this.protesta;
	}

	public void setProtesta(String protesta) {
		this.protesta = protesta;
	}

	@Column(name = "TEM_SEGURO", nullable = false, length = 1)
	public String getTemSeguro() {
		return this.temSeguro;
	}

	public void setTemSeguro(String temSeguro) {
		this.temSeguro = temSeguro;
	}

	@Column(name = "LIMITE_FRETE", precision = 0)
	public Double getLimiteFrete() {
		return this.limiteFrete;
	}

	public void setLimiteFrete(Double limiteFrete) {
		this.limiteFrete = limiteFrete;
	}

	@Column(name = "CARTEIRA", nullable = false, length = 3)
	public String getCarteira() {
		return this.carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	@Column(name = "TAXA", length = 1)
	public String getTaxa() {
		return this.taxa;
	}

	public void setTaxa(String taxa) {
		this.taxa = taxa;
	}

	@Column(name = "DIA_VENCIMENTO", nullable = false, precision = 0)
	public Integer getDiaVencimento() {
		return this.diaVencimento;
	}

	public void setDiaVencimento(Integer diaVencimento) {
		this.diaVencimento = diaVencimento;
	}

	@Column(name = "PRAZO_PAGAMENTO", nullable = false, precision = 0)
	public Integer getPrazoPagamento() {
		return this.prazoPagamento;
	}

	public void setPrazoPagamento(Integer prazoPagamento) {
		this.prazoPagamento = prazoPagamento;
	}
}