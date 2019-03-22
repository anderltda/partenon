package br.com.project.foundation.service;

import br.com.project.foundation.exception.ServiceException;


public class SegurancaException extends ServiceException {
	private static final long serialVersionUID = 1L;
	
	public SegurancaException() {
		super();
	}
	
	public SegurancaException(String message) {
		super(message);
	}
}
