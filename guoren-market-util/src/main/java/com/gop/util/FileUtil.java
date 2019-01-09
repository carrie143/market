/**
 * 文件工具类
 *
 * @author xiezz
 * @version 1.1.3
 */
package com.gop.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FileUtil {

	public static String getBase64FromFile(String fileName) {
		FileInputStream inputFile = null;
		try {
			File file = new File(fileName);
			inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			return Base64.getEncoder().encodeToString(buffer);
		} catch (Exception e) {
			return "";
		} finally {
			if (inputFile != null)
				try {
					inputFile.close();
				} catch (IOException e) {
				}
		}
	}

	public static void writeFileFromBase64(String base64, String fileName) throws IOException {
		FileOutputStream out = null;
		try {
			byte[] buffer = Base64.getEncoder().encode(base64.getBytes());
			out = new FileOutputStream(fileName);
			out.write(buffer);
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
				}
		}
	}
}
