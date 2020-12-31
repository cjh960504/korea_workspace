package com.koreait.fashionshop.common;
import java.io.File;

public class FileManager {
	private String saveDir;
	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}
	public String getSaveDir() {
		return saveDir;
	}
	
	public static String getExtend(String path) {
		int start = path.lastIndexOf(".");
		int end = path.length();
		String ext = path.substring(start+1, end);
		
		return ext;
	}
	public static boolean deleteFile(String path) {
		File file = new File(path);		
		return file.delete();
	}
}
