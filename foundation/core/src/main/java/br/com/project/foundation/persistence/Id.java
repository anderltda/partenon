package br.com.project.foundation.persistence;

import java.io.Serializable;

public interface Id extends Serializable {
	public static final long serialVersionUID = 1L;

	public void setValue(String value);

	public String getValue();
}
