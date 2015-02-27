/**
 **作者：翁加林
 **时间：2012-8-13
 **文件名：QuestionBank_single_update_image.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest11
 */

package org.wjlmgqs.web.controller.profession;

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
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.SingleServiceImp;
import org.wjlmgqs.web.util.ImageUpload;
import org.wjlmgqs.web.util.StaticVariable;
import org.wjlmgqs.web.util.WebLogger;

public class QuestionBank_single_update_image extends HttpServlet {
	private static final long serialVersionUID = -5453183859112872700L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
		String showMessage = "信息提示：上传图片失败！";
		String saveImageTitle = request.getParameter("singleId");
		String saveImageFormat = StaticVariable.getDefaultImageFormat();
		String saveImageName = teacher.getUserId()+ImageUpload.QuestionType_Single+saveImageTitle + saveImageFormat;//教师编号+试题类型+题号
		ServletContext servletContext = request.getSession().getServletContext();
		String saveFolder = servletContext.getInitParameter("questionBankFolder");
		String realPath = servletContext.getRealPath("/");
		String contextPath = servletContext.getContextPath();
		Long imageSizeLimit = Long.parseLong(servletContext.getInitParameter("imageSizeLimit"));
		ImageUpload uploadBulider = new ImageUpload();
		ServletFileUpload upload = uploadBulider.getUploadInstance(saveFolder);// 返回upload对象，并检测上传文件夹是否存在，不在就创建
		try {
			@SuppressWarnings("rawtypes")
			Iterator it = upload.parseRequest(request).iterator();
			while (it.hasNext()) {
				FileItem item = (FileItem) it.next();
				if (!item.isFormField()) {
					String name = item.getName();// 图片名
					long size = item.getSize();// 图片大小
					String validateResult = uploadBulider.validateImageFormat(name, size, imageSizeLimit);
					if (validateResult != null) {
						showMessage = validateResult;
						break;
					} else {
						File saveImage = null;
						int times = 3;
						try {
							do {// 在本地生成文件名：
								saveImage = new File(realPath + saveFolder 	+ "/" + saveImageName);
								//WebLogger.showInfor("本地文件路径:"+saveFile.getAbsolutePath());
								item.write(saveImage);
								if (times < 1) {
									break;
								} else {
									times--;
								}
							} while (!saveImage.exists());
							SingleServiceImp singleService = new SingleServiceImp();
							Single single = singleService.getSingleById(Integer.parseInt(saveImageTitle),teacher);
							single.setImage(saveImageName);// 数据库中保存路径
							boolean result = singleService.updateSingleImage(single);
							if (result) {
								showMessage = contextPath + "/" + saveFolder + "/" + saveImageName;// 网络文件路径
								WebLogger.showInfor("网络文件路径:"+showMessage);
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
		response.getWriter().print(showMessage);// 正确返回时，返回新图片的地址
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
