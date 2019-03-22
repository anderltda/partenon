package br.com.project.commons.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
	public static void writeToFile(final String fileName, final List<String> values) throws IOException {
		// BufferedWriter out = null;
		// try {
		// out = new BufferedWriter(new FileWriter(fileName, true));
		// for (String string : values) {
		// out.write(string);
		// }
		// } finally {
		// if (out != null)
		// try {
		// out.flush();
		// } catch (IOException e) {
		// }
		// if (out != null)
		// try {
		// out.close();
		// } catch (IOException e) {
		// }
		// }

		FileOutputStream fout = new FileOutputStream(fileName, true);
		FileChannel fc = fout.getChannel();
		for (String string : values) {
			fc.write(ByteBuffer.wrap(string.getBytes()));
		}
		fc.close();
		fout.close();
	}

	public static void writeToFile(final String fileName, final String value) throws IOException {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(fileName, true));
			out.write(value);
		} finally {
			if (out != null) try {
				out.flush();
			} catch (IOException e) {
			}
			if (out != null) try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void wipeFile(final String fileName) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(fileName, false));
			out.flush();
			out.close();
		} catch (IOException e) {
			return;
		}
	}

	public static final List<File> getFileList(final String baseDirectory, final boolean recursive) {
		File directory = new File(baseDirectory.trim());
		if (directory.isDirectory()) {
			List<File> returnValue = new ArrayList<File>();
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				File f = new File(directory, files[i]);
				if (f.isDirectory()) {
					returnValue.addAll(getFileList(f.getAbsolutePath(), true));
				} else {
					returnValue.add(f);
				}
			}
			return returnValue;

		} else {
			return new ArrayList<File>();
		}
	}

	public static final List<File> getFileList(final String baseDirectory, final boolean recursive, String regEx) {
		File directory = new File(baseDirectory.trim());
		if (directory.isDirectory()) {
			List<File> returnValue = new ArrayList<File>();
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				File f = new File(directory, files[i]);
				if (f.isDirectory()) {
					returnValue.addAll(getFileList(f.getAbsolutePath(), true, regEx));
				} else {
					if (f.getName().matches(regEx)) {
						returnValue.add(f);
					}
				}
			}
			return returnValue;

		} else {
			return new ArrayList<File>();
		}
	}

	public static final List<File> getFileList(final String diretorioBase) {
		return getFileList(diretorioBase, false);
	}

	public static final StringBuilder getStringContent(File file) throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = in.readLine()) != null)
				builder.append(str.trim());
			return builder;
		} finally {
			if (in != null) in.close();
		}
	}

	public static final List<String> getLines(File file) throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			List<String> lines = new ArrayList<String>();
			String str;
			while ((str = in.readLine()) != null)
				lines.add(str);
			return lines;
		} finally {
			if (in != null) in.close();
		}
	}

	public static final List<String> getLines(String file, String encoding) throws IOException {
		BufferedReader in = null;
		try {
			List<String> lines = new ArrayList<String>();
			Scanner scanner = new Scanner(new FileInputStream(file), "windows-1252");
			try {
				while (scanner.hasNextLine()) {
					lines.add(scanner.nextLine());
				}
			} finally {
				scanner.close();
			}

			return lines;
		} finally {
			if (in != null) in.close();
		}
	}

	public static final List<String> readLines(BufferedReader input, int count) throws IOException {
		List<String> lines = new ArrayList<String>();
		String str;
		int i = 0;
		while (i < count && (str = input.readLine()) != null) {
			lines.add(str);
			i++;
		}
		return lines;
	}

	public static final void copy(String orig, String dest) throws IOException {
		File inputFile = new File(orig);
		File outputFile = new File(dest);

		FileReader in = new FileReader(inputFile);
		FileWriter out = new FileWriter(outputFile);
		int c;

		while ((c = in.read()) != -1)
			out.write(c);

		in.close();
		out.close();
	}

	public static void delete(String path) {
		if (StringUtil.isEmpty(path)) {
			return;
		}

		StringBuilder builder = new StringBuilder();
		builder.append(path);
		File file = new File(builder.toString());

		if (file.exists()) {
			file.delete();
		}
	}

	public static void image(byte[] bytes, String fileName) {

		File file = new File(fileName);
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(bytes);
			bos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} 

	}
}
