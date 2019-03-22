package br.com.project.commons.exception;

public class BaseException extends Exception {
	private static final long serialVersionUID = 1L;
	protected int id;
	protected String codigo;
	protected String msgBundleError;
	protected String[] params;

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, String... params) {
		super(message);
		this.params = params;
	}

	public BaseException(int id, String message) {
		super(message);
		this.id = id;
	}

	public BaseException(String codigo, String message) {
		super(message);
		this.codigo = codigo;
	}

	public BaseException(Throwable t) {
		super(t);
	}

	public BaseException(String message, Throwable t) {
		super(message, t);
	}

	public BaseException(int id, Throwable t) {
		super(t);
		this.id = id;
	}

	public BaseException(int id, String message, Throwable t) {
		super(message, t);
		this.id = id;
	}

	public BaseException(String codigo, String message, Throwable t) {
		super(message, t);
		this.codigo = codigo;
	}

	public BaseException(int id, String codigo, String message, Throwable t) {
		super(message, t);
		this.id = id;
		this.codigo = codigo;
	}

	public BaseException(int id, String codigo, String message, Throwable t, String msgBundleError) {
		super(message, t);
		this.id = id;
		this.codigo = codigo;
		this.msgBundleError = msgBundleError;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMsgBundleError() {
		return msgBundleError;
	}

	public void setMsgBundleError(String msgBundleError) {
		this.msgBundleError = msgBundleError;
	}

	public boolean hasParams() {
		return params != null;
	}

	public String[] getParams() {
		return params;
	}

}