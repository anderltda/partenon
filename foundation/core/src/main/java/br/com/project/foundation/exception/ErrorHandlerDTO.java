package br.com.project.foundation.exception;

import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.StringUtil;

public class ErrorHandlerDTO extends BaseObject {
	private static final long serialVersionUID = 8651009334478451867L;
	protected String contraintName;
	protected String message;
	protected String additionalMessage;
	protected String parentTable;
	protected String childTable;
	protected Integer id;

	public String getContraintName() {
		return contraintName;
	}

	public void setContraintName(String contraintName) {
		this.contraintName = contraintName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = StringUtil.removeControlChar(message);
	}

	public String getAdditionalMessage() {
		return additionalMessage;
	}

	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}

	public String getParentTable() {
		return parentTable;
	}

	public void setParentTable(String parentTable) {
		this.parentTable = parentTable;
	}

	public String getChildTable() {
		return childTable;
	}

	public void setChildTable(String childTable) {
		this.childTable = childTable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ReflectionUtil.toString(this);
	}

}
