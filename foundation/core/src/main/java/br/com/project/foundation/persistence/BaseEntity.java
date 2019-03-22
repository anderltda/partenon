package br.com.project.foundation.persistence;

import java.io.Serializable;

public interface BaseEntity<ID extends Serializable> extends Serializable {
	
	// indica se esta entidade é reentrante
	boolean isReentrant();

	// set para reentrante
	void setReentrant(boolean flag);

	// indica se o Id é nulo
	boolean isIdNull();

	// indica se validação precisa ser aplicada
	boolean validate();

	// indica se validação precisa ser aplicada
	void setValidate(boolean flag);

}