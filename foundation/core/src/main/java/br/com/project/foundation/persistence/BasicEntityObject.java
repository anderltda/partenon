package br.com.project.foundation.persistence;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import br.com.project.commons.annotation.Configuration;
import br.com.project.commons.util.NumberUtil;

@MappedSuperclass
@Configuration
public abstract class BasicEntityObject<ID extends Serializable> extends NonVersionableBasicEntityObject<ID> implements BaseEntity<ID> {
	private static final long serialVersionUID = 1L;

	private Long version;

//	@Version
//	@Column(name = "ora_rowscn", updatable = false, insertable = false)
//	@Generated(GenerationTime.ALWAYS)
	@Transient
	public Long getVersion() {
		return version;
	}

	@Transient
	public void setVersion(Long ver) {
		this.version = ver;
	}

	@Transient
	public final Long getOraRowscn() {
		return this.version;
	}

	@Transient
	public final void setOraRowscn(Long oraRowscn) {
		this.version = oraRowscn;
	}

	@Transient
	public boolean isOutOfDate(Long otherVersion) {
		return NumberUtil.longValue(version) == NumberUtil.longValue(otherVersion);
	}
	
}
