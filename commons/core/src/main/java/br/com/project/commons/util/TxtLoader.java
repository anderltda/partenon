package br.com.project.commons.util;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class TxtLoader implements Loader {
	public static final int[] MASK_DIMEP = {15, 21, 25, 26, 28,31, 32, 33};
	
	private List<String> lines;
	private int[] mask;

	public TxtLoader(String fileName, int[] mask) throws Exception {
		open(new File(fileName));
		this.mask = mask;
	}

	public TxtLoader(File file, int[] mask) throws Exception {
		open(file);
		this.mask = mask;
	}

	protected void open(File file) throws IOException {
		lines = FileUtil.getLines(file);
	}

	public int getTotalRows() {
		return lines.size();
	}

	public int getTotalColumns() {
		return mask.length; 
	}

	public String getValue(int rowNumber, int columnNumber) throws Exception {
		String line = lines.get(rowNumber);
		int begin = columnNumber > 0 ? mask[columnNumber - 1] : 0;
		int end = mask[columnNumber];
	
		return line.substring(begin, end);
	}
}
