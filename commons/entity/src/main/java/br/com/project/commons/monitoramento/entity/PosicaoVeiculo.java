package br.com.project.commons.monitoramento.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.project.commons.cadastro.entity.Veiculo;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.StringUtil;

/**
 * PosicaoVeiculo entity. 
 * @author anderson.nascimento
 *
 */
@Entity
@Table(name = "POSICAO_VEICULO")
public class PosicaoVeiculo extends BaseObject {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date data;
	private Veiculo veiculo;
	private String longitude;
	private String latitude;
	private String descricao;

	private Date dataInicio;
	private Date dataFim;
	
	@Id
	@Column(name = "ID_POSICAO", unique = true, nullable = false, precision = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VEICULO", nullable = false)
	public Veiculo getVeiculo() {
		return this.veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA", nullable = false)
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name = "LONGITUDE", nullable = false, length = 30)
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "LATITUDE", nullable = false, length = 30)
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "DESCRICAO", nullable = false, length = 200)
	public String getDescricao() {
		return StringUtil.isNotEmpty(this.descricao) ? this.descricao : null;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Transient
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@Transient
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
}