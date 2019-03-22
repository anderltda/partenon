package br.com.project.foundation.persistence.impl;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenerationTime;

import br.com.project.commons.util.NumberUtil;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.foundation.persistence.BaseEntity;

@MappedSuperclass
public abstract class BaseEntityImpl<ID extends Serializable> implements BaseEntity<ID> {

	protected ID id;

	private static final long serialVersionUID = -2917257182425261629L;

	@Transient
	protected boolean reentrant = false;

	@Transient
	protected boolean validate = true;

	private Long version;

	@Transient
	public abstract ID getId();

	@Transient
	public abstract void setId(ID id);

	@Transient
	public boolean isReentrant() {
		return reentrant;
	}

	@Transient
	public void setReentrant(boolean reentrant) {
		this.reentrant = reentrant;
	}

	@Transient
	public boolean validate() {
		return validate;
	}

	@Transient
	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	@Transient
	public boolean isIdNull() {
		return ReflectionUtil.isIdNull(this);
	}

	@Version
	@Column(name = "ora_rowscn", updatable = false, insertable = false)
	//@Generated(GenerationTime.ALWAYS)
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long ver) {
		this.version = ver;
	}

	@Transient
	public boolean isOutOfDate(Long otherVersion) {
		return NumberUtil.longValue(version) == NumberUtil.longValue(otherVersion);
	}

}

