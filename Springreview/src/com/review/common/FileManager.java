/*
 * ���ϰ� ���õ� ������ ����� ��Ƴ��� Ŭ����
 * */
package com.review.common;

import java.io.File;

public class FileManager {
	//Ȯ���ڸ� �����ϱ�
	public static String getExtend(String path) {
		int start = path.lastIndexOf(".");
		int end = path.length();
		String ext = path.substring(start+1, end);
		
		return ext;
	}
	
	//���� ����( ȣ���ڴ� ���� ���ϴ� ���� ��� �ѱ��)
	public static boolean deleteFile(String path) {
		File file = new File(path);		
		return file.delete();
	}
	/*
	//�����׽�Ʈ �غ��� ����
	public static void main(String[] args) {
		String filename="c:\\photo\\summer\\2010\\��������.����.jpg";
		System.out.println(getExtend(filename));
	}
	*/
}
