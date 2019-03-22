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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import br.com.project.commons.annotation.filter.DialogFilter;
import br.com.project.commons.annotation.filter.SelectFilter;
import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.entity.BasicLoggableEntityObject;

/**
 * Cidade entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CIDADE", uniqueConstraints = @UniqueConstraint(columnNames = {"CIDADE", "ESTADO" }))
public class Cidade extends BasicLoggableEntityObject<Long> {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@DialogFilter(label="lbl_filial", view = "filialView", name="filial.sigla", openDialog="#singleDialogFilial")
	private Filial filial;

	@TextFilter(label="lbl_cidade", length = 200)
	private String cidade;
	
	@SelectFilter(label="lbl_estado", style="width: 100px;" ,method="#{enderecoView.getEstados()}")
	private String estado;
		
	private String area;
	private Integer codSecFazenda;
	private Integer codIbge;
	private String atendRedespachante;
	private Boolean autentica;
	
	
	@Id
	@Column(name = "ID_CIDADE", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_CIDADE")
	@SequenceGenerator(name = "SEQ_CIDADE", sequenceName = "SEQ_CIDADE")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CIDADE", nullable = false, length = 200)
	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Column(name = "ESTADO", nullable = false, length = 2)
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FILIAL", nullable = false)
	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	@Column(name = "AREA", nullable = false, length = 3)
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "COD_SEC_FAZENDA", precision = 0)
	public Integer getCodSecFazenda() {
		return this.codSecFazenda;
	}

	public void setCodSecFazenda(Integer codSecFazenda) {
		this.codSecFazenda = codSecFazenda;
	}

	@Column(name = "COD_IBGE", nullable = false, precision = 0)
	public Integer getCodIbge() {
		return this.codIbge;
	}

	public void setCodIbge(Integer codIbge) {
		this.codIbge = codIbge;
	}

	@Column(name = "ATEND_REDESPACHANTE", nullable = false, length = 1)
	public String getAtendRedespachante() {
		return this.atendRedespachante;
	}

	public void setAtendRedespachante(String atendRedespachante) {
		this.atendRedespachante = atendRedespachante;
	}
	
	@Transient
	public Boolean getAutentica() {		
		return autentica;
	}

	public void setAutentica(Boolean autentica) {
		this.autentica = autentica;
	}
	
}