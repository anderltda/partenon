package br.com.project.commons.util;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String msgBundleError;
	protected String msgDescError;
	protected boolean ok;
	public int size;

	public Log log() {
		return LogFactory.getLog(getClass().getName());
	}

	public String getMsgBundleError() {
		return msgBundleError;
	}

	public void setMsgBundleError(String msgBundleError) {
		this.msgBundleError = msgBundleError;
	}

	public String getMsgDescError() {
		return msgDescError;
	}

	public void setMsgDescError(String msgDescError) {
		this.msgDescError = msgDescError;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Object getId() {
		return null;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}	
}