package br.com.project.commons.seguranca.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.project.commons.annotation.filter.SelectFilter;
import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.StringUtil;

/**
 * Usuario entity.
 * @author anderson.nascimento
 */
@Entity
@Table(name = "USUARIO")
public class Usuario extends BaseObject {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@TextFilter(label="lbl_login", icon="fa fa-share-alt", length = 200)
	private String login;

	@TextFilter(label="lbl_nome", icon="fa fa-share-alt", length = 200)
	private String nome;
		
	@TextFilter(label="lbl_email", icon="fa fa-share-alt", length = 400)
	private String email;	
	
	@SelectFilter(label="lbl_ativo", style="width: 100px;", method="#{usuarioView.comboSelectAtivo()}")
	private String ativo;

	//@DateFilter(label="lbl_data_ultimo_acesso")
	private Date dataUltimoAcesso;

	private String password;	

	@Id
	@Column(name = "ID_USUARIO", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "SEQ_USUARIO")
	@SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NOME", nullable = false, length = 100)
	public String getNome() {
		return StringUtil.isEmpty(this.nome) ? null : this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "EMAIL", nullable = false, length = 100)
	public String getEmail() {
		return StringUtil.isEmpty(this.email) ? null : this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PASSWORD", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ATIVO", nullable = false, length = 1)
	public String getAtivo() {
		return StringUtil.isEmpty(this.ativo) ? null : this.ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	@Column(name = "LOGIN", nullable = false, length = 100)
	public String getLogin() {
		return StringUtil.isEmpty(this.login) ? null : this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ULTIMO_ACESSO")
	public Date getDataUltimoAcesso() {
		return this.dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

}