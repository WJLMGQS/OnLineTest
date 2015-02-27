/**
 **@author wjlmgqs
 **下午6:02:53
 **Account_update_photo.java
 **org.wjlmgqs.web.controller.student
 **OnLineTest
 */
package org.wjlmgqs.web.controller.student;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.service.impl.StudentServiceImp;
import org.wjlmgqs.web.util.ImageUpload;
import org.wjlmgqs.web.util.StaticVariable;

public class Account_update_photo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = "上传图片失败！";
		String saveImageTitle = request.getParameter("studentId");
		String saveImageFormat = StaticVariable.getDefaultImageFormat();
		String saveImageName = saveImageTitle+saveImageFormat;
		ServletContext servletContext = request.getSession().getServletContext();
		String saveFolder =	servletContext.getInitParameter("studentPhotoFolder");
		String realPath = servletContext.getRealPath("/");
		String contextPath =  servletContext.getContextPath();
		Long imageSizeLimit = Long.parseLong(servletContext.getInitParameter("imageSizeLimit"));
		ImageUpload uploadBulider = new ImageUpload();
		ServletFileUpload upload = uploadBulider.getUploadInstance(saveFolder);//返回upload对象，并检测上传文件夹是否存在，不在就创建
		try {
			Iterator<?> it = upload.parseRequest(request).iterator();
			while (it.hasNext()) {
				FileItem item = (FileItem) it.next();
				if (!item.isFormField()) {
					String name = item.getName();//图片名
					long size = item.getSize();//图片大小
					String validateResult = uploadBulider.validateImageFormat(name, size, imageSizeLimit);
					if (validateResult!=null) {
						showMessage = validateResult;
						break;
					}else {
						File saveImage = null;
						int times = 3;
						try {
							do {//在本地生成文件名：
								saveImage = new File(realPath+saveFolder +"/" + saveImageName );
								//System.out.println("saveFile:"+saveFile.getAbsolutePath());//本地文件路径
								item.write(saveImage);
								if(times<1) {
									break;
								}else {
									times--;
								}
							} while (!saveImage.exists());
							StudentServiceImp studentService = new StudentServiceImp();
							Student student = studentService.getStudentById(saveImageTitle);
							student.setPhoto(saveImageName);//数据库中保存路径
							boolean result = studentService.updateStudentPhoto(student);
							if(result) {
								showMessage =contextPath+"/"+saveFolder+"/"+saveImageName;//网络文件路径
							}
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}
			}
		} catch (FileUploadException ex) {
			ex.printStackTrace();
		}
		response.getWriter().print(showMessage);//正确返回时，返回新图片的地址
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
}
