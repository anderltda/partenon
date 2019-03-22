package br.com.project.foundation.persistence;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import br.com.project.commons.annotation.Configuration;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.ReflectionUtil;

@MappedSuperclass
@Configuration
public abstract class NonVersionableBasicEntityObject<ID extends Serializable> extends BaseObject implements BaseEntity<ID> {
	private static final long serialVersionUID = 1L;

	@Transient
	protected boolean reentrant = false;

	@Transient
	protected boolean validate = true;

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

	@Transient
	public Class<?> getConcreteClass() {
		return ReflectionUtil.getConcreteClass(this);
	}

	@Transient
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Transient
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getConcreteClass() != ReflectionUtil.getConcreteClass(obj))
			return false;
		NonVersionableBasicEntityObject<?> other = (NonVersionableBasicEntityObject<?>) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	
	@Transient
	public Object getValue(String property) {
		return ReflectionUtil.executeGetterMethod(this, property);
	}
}
