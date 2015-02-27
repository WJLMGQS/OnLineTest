/**
**作者：翁加林
**时间：2012-8-13
**文件名：ImageUpload.java
**包名：org.wjlmgqs.web.util
**工程名：OnLineTest11
*/


package org.wjlmgqs.web.util;

import java.io.File;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ImageUpload {
	private static ServletFileUpload upload = null;
	public static String QuestionType_Single = "_s_";
	public static String QuestionType_Multiple = "_m_";
	public static String QuestionType_Judge = "_j_";
	public static String QuestionType_FillBlank = "_f_";
	static {
		DiskFileItemFactory fac = new DiskFileItemFactory();
		upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
	}
	public ServletFileUpload getUploadInstance(String saveFolder) {
		File baseFile = new File(saveFolder);
		if (!baseFile.exists()) {
			baseFile.mkdirs();
		}
		return upload;
	}
	
	/*
	 * *.jpg;*.gif;*.jpeg;*.png;*.bmp
	 * */
	public String validateImageFormat(String name,long imageSize,long limitSize) {
		if(name==null) {
			return "图片上传失败！";
		}
		String format = name.substring(name.lastIndexOf(".")+1).toLowerCase();
		if(format==null||!format.equals("jpg")&&!format.equals("gif")&&!format.equals("jpeg")&&!format.equals("png")&&!format.equals("bmp")) {
			return "上传图片格式不正确";
		}else if(imageSize>limitSize){
			return  "上传图片大小为："+limitSize+"KB之内！";
		}
		return null;
	}

}
