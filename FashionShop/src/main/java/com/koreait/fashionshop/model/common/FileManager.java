package com.koreait.fashionshop.model.common;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Component /*component-scan의 대상 중 하나임*/
public class FileManager {
	private static final Logger logger = LoggerFactory.getLogger(FileManager.class);
	private String saveBasicDir="/resources/data/basic";
	private String saveAddOnDir="/resources/data/addon";
	
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
	
	//파일 저장하기
	public void saveFile(String readDir, MultipartFile multi) {
		try {
			multi.transferTo(new File(readDir));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
