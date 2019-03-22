package br.com.project.commons.util;

public interface Loader {
	
	int getTotalRows();
	
	int getTotalColumns();
	
	String getValue(int rowNumber, int columnNumber) throws Exception;
	
}
