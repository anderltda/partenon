package br.com.project.foundation.exception;

import br.com.project.commons.exception.BaseException;


public class BusinessException extends BaseException {
	private static final long serialVersionUID = 2098455903773256065L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(int id, String message) {
		super(id, message);
	}

	public BusinessException(String codigo, String message) {
		super(codigo, message);
	}

	public BusinessException(Throwable t) {
		super(t);
	}

	public BusinessException(String message, Throwable t) {
		super(message, t);
	}

	public BusinessException(int id, Throwable t) {
		super(id, t);
	}

	public BusinessException(int id, String message, Throwable t) {
		super(id, message, t);
	}

	public BusinessException(String codigo, String message, Throwable t) {
		super(codigo, message, t);
	}

	public BusinessException(int id, String codigo, String message, Throwable t) {
		super(id, codigo, message, t);
	}

}