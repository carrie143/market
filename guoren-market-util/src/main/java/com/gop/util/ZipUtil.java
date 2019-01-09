package com.gop.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Joiner;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.util.Zip4jUtil;

public class ZipUtil {
	public static File[] unZip(String destPath, File file) throws Exception {
		if (!Zip4jUtil.checkFileExists(file)) {
			throw new FileNotFoundException("can not find exception!");
		}
		ZipFile zipFile = new ZipFile(file);
		if (!zipFile.isValidZipFile()) {
			throw new ZipException("压缩文件不合法,可能被损坏.");
		}
		zipFile.extractAll(destPath);
		List<FileHeader> fileHeaders = zipFile.getFileHeaders();
		List<File> extractedFileList = new ArrayList<File>();
		for (FileHeader fileHeader : fileHeaders) {
			if (!fileHeader.isDirectory()) {
				extractedFileList.add(new File(destPath, fileHeader.getFileName()));
			}
		}
		File[] extractedFiles = new File[extractedFileList.size()];
		extractedFileList.toArray(extractedFiles);
		return extractedFiles;
	}

	public static String unZipOneTxt(File file) throws Exception {
		if (!Zip4jUtil.checkFileExists(file)) {
			throw new FileNotFoundException("can not find exception!");
		}
		ZipFile zipFile = new ZipFile(file);
		if (!zipFile.isValidZipFile()) {
			throw new ZipException("压缩文件不合法,可能被损坏.");
		}
		List<FileHeader> fileHeaders = zipFile.getFileHeaders();
		if (fileHeaders.size() == 0) {
			throw new ZipException("压缩文件已经被损坏");
		}
		String result = null;
		InputStream inputStream = null;
		try {
			inputStream = zipFile.getInputStream(fileHeaders.get(0));
			result = IOUtils.toString(inputStream, "UTF-8");
		} catch (Exception e) {
			throw e;
		} finally {
			inputStream.close();
		}
		return result;

	}

	public static String unZipOneTxt(byte[] zippedByte) throws Exception {
		FileOutputStream fs = null;
		ByteArrayInputStream bs = null;
		File tempfile = null;
		try {
			tempfile = new File(Joiner.on("-").join("temp", DateTimeUtil.getDate()));
			fs = new FileOutputStream(tempfile);
			bs = new ByteArrayInputStream(zippedByte);
			int read = -1;
			while (-1 != (read = bs.read())) {
				fs.write(read);
			}
			fs.flush();
		} catch (Exception e) {

		} finally {
			fs.close();
			bs.close();
		}
		String result = unZipOneTxt(tempfile);
		tempfile.delete();
		return result;
	}

}
