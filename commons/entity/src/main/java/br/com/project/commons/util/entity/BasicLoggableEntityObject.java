package br.com.project.commons.util.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.commons.util.DateTimeUtil;
import br.com.project.foundation.persistence.BasicEntityObject;
import br.com.project.foundation.persistence.BasicLoggableInterface;


/**
 * @author anderson.nascimento
 *
 * @param <ID>
 */
@MappedSuperclass
public abstract class BasicLoggableEntityObject<ID extends Serializable> extends BasicEntityObject<ID> implements BasicLoggableInterface {
	
	private static final long serialVersionUID = 1L;

	private Date dataAlteracao;
	private Date dataCriacao;
	private Long idUsuarioAlteracao;
	private Long idUsuarioCriacao;	
	private String usuarioCriacaoFormatado;
	private String usuarioAlteracaoFormatado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ALTERACAO")
	public Date getDataAlteracao() {
		return this.dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CRIACAO")
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Column(name = "ID_USUARIO_ALTERACAO", nullable = true, precision = 10, scale = 0)
	public Long getIdUsuarioAlteracao() {
		return this.idUsuarioAlteracao;
	}

	public void setIdUsuarioAlteracao(Long idUsuarioAlteracao) {
		this.idUsuarioAlteracao = idUsuarioAlteracao;
	}

	@Column(name = "ID_USUARIO_CRIACAO", nullable = true, precision = 10, scale = 0)
	public Long getIdUsuarioCriacao() {
		return this.idUsuarioCriacao;
	}

	public void setIdUsuarioCriacao(Long idUsuarioCriacao) {
		this.idUsuarioCriacao = idUsuarioCriacao;
	}
	
	/**
	 * Usuário de Criação e Atualização para facilidades não deve ser usado para
	 * insert. Método setter não deve ser usado
	 */
	Usuario usuarioCriacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO_CRIACAO", nullable = false, insertable = false, updatable = false)
	public Usuario getUsuarioCriacao() {
		return this.usuarioCriacao;
	}

	Usuario usuarioAlteracao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO_ALTERACAO", nullable = false, insertable = false, updatable = false)
	public Usuario getUsuarioAlteracao() {
		return this.usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	
	@Transient
	public String getUsuarioCriacaoFormatado() {
		usuarioCriacaoFormatado = usuarioCriacao.getNome() +" - "+ DateTimeUtil.formatDate(dataCriacao, DateTimeUtil.DEFAULT_DATETIME_PATTERN);
		return usuarioCriacaoFormatado;
	}
	
	public void setUsuarioCriacaoFormatado(String usuarioCriacaoFormatado) {
		this.usuarioCriacaoFormatado = usuarioCriacaoFormatado;
	}

	@Transient
	public String getUsuarioAlteracaoFormatado() {
		usuarioAlteracaoFormatado = usuarioAlteracao.getNome() +" - "+ DateTimeUtil.formatDate(dataAlteracao, DateTimeUtil.DEFAULT_DATETIME_PATTERN);
		return usuarioAlteracaoFormatado;
	}

	public void setUsuarioAlteracaoFormatado(String usuarioAlteracaoFormatado) {
		this.usuarioAlteracaoFormatado = usuarioAlteracaoFormatado;
	}	
}
