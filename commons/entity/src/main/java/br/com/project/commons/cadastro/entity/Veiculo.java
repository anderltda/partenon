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
import javax.persistence.UniqueConstraint;

import br.com.project.commons.annotation.filter.DialogFilter;
import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * Veiculo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "VEICULO", uniqueConstraints = @UniqueConstraint(columnNames = "PLACA"))
public class Veiculo extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;
	
	private Long id;
		
	@DialogFilter(label="lbl_filial", view = "filialView", name="filial.sigla", openDialog="#singleDialogFilial")
	private Filial filial;

	@DialogFilter(label="lbl_proprietario", view = "motoristaView", name="proprietario.nome", actionListener="openProprietarioDialog", openDialog="#singleDialogMotorista")
	private Motorista proprietario;

	@DialogFilter(label="lbl_motorista", view = "motoristaView", name="motorista.nome", actionListener="openMotoristaDialog", openDialog="#singleDialogMotorista")
	private Motorista motorista;

	@TextFilter(label="lbl_placa", style="width:100px; text-transform:uppercase;", icon="fa fa-share-alt", length = 8)
	private String placa;
		
	private String frota;
	private String categoria;
	private String combustivel;
	private String uf;
	private String cidade;
	private String modelo;
	private String marca;
	private String chassi;
	private String nrMotor;
	private String certificado;
	private String renavam;
	private String alienacao;
	private String numeroApolice;
	private String rastreamentoCia;
	private String rastreamentoTipo;
	private String corCabine;
	private String corCarroceria;
	private Integer qtdEixo;
	private Integer ano;
	private Integer capacidadeM3;
	private Double capacidadeKg;
	private Double tara;
	private Double altura;
	private Double largura;
	private Double comprimento;
	private byte[] imagem;
	private Date dataLicenciamentoVenc;
	private Date dataApoliceVenc;
	
	public Veiculo() {
		super();
	}

	public Veiculo(String placa) {
		this.placa = placa;
	}
	
	@Id
	@Column(name = "ID_VEICULO", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_VEICULO")
	@SequenceGenerator(name = "SEQ_VEICULO", sequenceName = "SEQ_VEICULO")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FILIAL")
	public Filial getFilial() {
		return this.filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	@Column(name = "PLACA", unique = true, nullable = false, length = 8)
	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Column(name = "FROTA", length = 8)
	public String getFrota() {
		return this.frota;
	}

	public void setFrota(String frota) {
		this.frota = frota;
	}

	@Column(name = "CATEGORIA", length = 20)
	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Column(name = "COMBUSTIVEL", length = 20)
	public String getCombustivel() {
		return this.combustivel;
	}

	public void setCombustivel(String combustivel) {
		this.combustivel = combustivel;
	}

	@Column(name = "QTD_EIXO", precision = 0)
	public Integer getQtdEixo() {
		return this.qtdEixo;
	}

	public void setQtdEixo(Integer qtdEixo) {
		this.qtdEixo = qtdEixo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROPRIETARIO", nullable = false)
	public Motorista getProprietario() {
		return proprietario;
	}

	public void setProprietario(Motorista proprietario) {
		this.proprietario = proprietario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MOTORISTA", nullable = false)
	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	@Column(name = "UF", length = 2)
	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Column(name = "CIDADE", length = 50)
	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Column(name = "MODELO", length = 50)
	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	@Column(name = "MARCA", length = 50)
	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Column(name = "ANO", precision = 0)
	public Integer getAno() {
		return this.ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	@Column(name = "CAPACIDADE_M3", precision = 0)
	public Integer getCapacidadeM3() {
		return this.capacidadeM3;
	}

	public void setCapacidadeM3(Integer capacidadeM3) {
		this.capacidadeM3 = capacidadeM3;
	}

	@Column(name = "CAPACIDADE_KG", precision = 15, scale = 3)
	public Double getCapacidadeKg() {
		return this.capacidadeKg;
	}

	public void setCapacidadeKg(Double capacidadeKg) {
		this.capacidadeKg = capacidadeKg;
	}

	@Column(name = "TARA", precision = 15, scale = 3)
	public Double getTara() {
		return this.tara;
	}

	public void setTara(Double tara) {
		this.tara = tara;
	}

	@Column(name = "ALTURA", precision = 15)
	public Double getAltura() {
		return this.altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	@Column(name = "LARGURA", precision = 15)
	public Double getLargura() {
		return this.largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	@Column(name = "COMPRIMENTO", precision = 15)
	public Double getComprimento() {
		return this.comprimento;
	}

	public void setComprimento(Double comprimento) {
		this.comprimento = comprimento;
	}

	@Column(name = "CHASSI", length = 22)
	public String getChassi() {
		return this.chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	@Column(name = "NR_MOTOR", length = 20)
	public String getNrMotor() {
		return this.nrMotor;
	}

	public void setNrMotor(String nrMotor) {
		this.nrMotor = nrMotor;
	}

	@Column(name = "CERTIFICADO", length = 50)
	public String getCertificado() {
		return this.certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	@Column(name = "RENAVAM", length = 15)
	public String getRenavam() {
		return this.renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_LICENCIAMENTO_VENC")
	public Date getDataLicenciamentoVenc() {
		return this.dataLicenciamentoVenc;
	}

	public void setDataLicenciamentoVenc(Date dataLicenciamentoVenc) {
		this.dataLicenciamentoVenc = dataLicenciamentoVenc;
	}

	@Column(name = "ALIENACAO", length = 50)
	public String getAlienacao() {
		return this.alienacao;
	}

	public void setAlienacao(String alienacao) {
		this.alienacao = alienacao;
	}

	@Column(name = "NUMERO_APOLICE", length = 50)
	public String getNumeroApolice() {
		return this.numeroApolice;
	}

	public void setNumeroApolice(String numeroApolice) {
		this.numeroApolice = numeroApolice;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_APOLICE_VENC")
	public Date getDataApoliceVenc() {
		return this.dataApoliceVenc;
	}

	public void setDataApoliceVenc(Date dataApoliceVenc) {
		this.dataApoliceVenc = dataApoliceVenc;
	}

	@Column(name = "RASTREAMENTO_CIA", length = 50)
	public String getRastreamentoCia() {
		return this.rastreamentoCia;
	}

	public void setRastreamentoCia(String rastreamentoCia) {
		this.rastreamentoCia = rastreamentoCia;
	}

	@Column(name = "RASTREAMENTO_TIPO", length = 50)
	public String getRastreamentoTipo() {
		return this.rastreamentoTipo;
	}

	public void setRastreamentoTipo(String rastreamentoTipo) {
		this.rastreamentoTipo = rastreamentoTipo;
	}

	@Column(name = "COR_CABINE", length = 30)
	public String getCorCabine() {
		return this.corCabine;
	}

	public void setCorCabine(String corCabine) {
		this.corCabine = corCabine;
	}

	@Column(name = "COR_CARROCERIA", length = 30)
	public String getCorCarroceria() {
		return this.corCarroceria;
	}

	public void setCorCarroceria(String corCarroceria) {
		this.corCarroceria = corCarroceria;
	}

	@Column(name = "IMAGEM")
	public byte[] getImagem() {
		return imagem;
	}
	
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
}