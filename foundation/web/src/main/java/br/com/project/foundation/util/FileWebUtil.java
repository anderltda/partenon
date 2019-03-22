package br.com.project.foundation.util;

import java.io.File;
import java.io.FileOutputStream;

import org.primefaces.model.UploadedFile;

public class FileWebUtil {
	public static File saveUploadedFile(UploadedFile file, String path) {
		File fileOut = null;
		try {
			fileOut = new File(path);
			fileOut.mkdirs();
			FileOutputStream fos = new FileOutputStream(fileOut);
			fos.write(file.getContents());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileOut;
	}
}
