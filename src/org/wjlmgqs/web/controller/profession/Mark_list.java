/**
 **@author wjlmgqs
 **下午9:58:49
 **Mark_list.java
 **org.wjlmgqs.web.controller.profession
 **OnLineTest
 */
package org.wjlmgqs.web.controller.profession;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.wjlmgqs.daomain.ScopeMark;
import org.wjlmgqs.service.impl.ScopeMarkServiceImp;
import org.wjlmgqs.web.util.MarkFlagUtil;
import org.wjlmgqs.web.util.WebLogger;

public class Mark_list extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String recordId = request.getParameter("recordId");
		int value = 0;
		try {
			value = Integer.parseInt(recordId);
			WebLogger.showInfor("开始准备数据，导出数据到Excel文件。");
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("学生考试成绩表");
			int rows_index = 0;//行标
			HSSFRow row = sheet.createRow(rows_index++);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			row.setRowStyle(style);
			buildRowTitle(row);
			ScopeMarkServiceImp scopeMarkServiceImp = new ScopeMarkServiceImp();
			List<ScopeMark> scopeMarks = scopeMarkServiceImp.loadMarkList(value);
			MarkFlagUtil.loadMarkFlag(scopeMarks);
			for (int i = 0; i < scopeMarks.size(); i++) {
				row = sheet.createRow(rows_index++);
				ScopeMark scopeMark = (ScopeMark) scopeMarks.get(i);
				buildRowContent(row,scopeMark);
			}
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("学生成绩下载_"+new Date().getTime()+".xls", "UTF-8"));
			response.setContentType("application/msexcel;charset=UTF-8");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
		} catch (Exception e) {
			WebLogger.showInfor("错误信息：生成Excel文件失败！");
			e.printStackTrace();
		}
		WebLogger.showInfor("导出数据到Excel文件完毕");
	}
	
	
	/**
	 * @see 根据传进来的对象，为每一行的单元格设值
	 */
	private void buildRowContent(HSSFRow row,ScopeMark scopeMark) {
		int index = 0;
		// 第四步，创建单元格，并设置值
		row.createCell(index++).setCellValue(scopeMark.getExamRecord().getName());
		row.createCell(index++).setCellValue(scopeMark.getExamRecord().getDescription());
		row.createCell(index++).setCellValue(scopeMark.getExamRecord().getFinishTime());
		row.createCell(index++).setCellValue(scopeMark.getExamRecord().getTotalMark().doubleValue());
		row.createCell(index++).setCellValue(scopeMark.getExamRecord().getSubject().getCode());
		row.createCell(index++).setCellValue(scopeMark.getStudent().getClasses().getCareer().getCode());
		row.createCell(index++).setCellValue(scopeMark.getStudent().getClasses().getCode());
		row.createCell(index++).setCellValue(scopeMark.getStudent().getUserId());
		row.createCell(index++).setCellValue(scopeMark.getStudent().getName());
		row.createCell(index++).setCellValue(scopeMark.getStudent_scope().doubleValue());
		row.createCell(index++).setCellValue(scopeMark.getMarkFlag().getClasses_avg().doubleValue());
		row.createCell(index++).setCellValue(scopeMark.getMarkFlag().getClass_order());
		row.createCell(index++).setCellValue(scopeMark.getMarkFlag().getCareer_avg().doubleValue());
		row.createCell(index++).setCellValue(scopeMark.getMarkFlag().getCareer_order());
		row.createCell(index++).setCellValue(scopeMark.getMarkFlag().getTotal_avg().doubleValue());
		row.createCell(index++).setCellValue(scopeMark.getMarkFlag().getTotal_order());
	}
	@SuppressWarnings("deprecation")
	private void buildRowTitle(HSSFRow row) {
		short index = 0;
		HSSFCell cell = null;
		String[] strs = {"考试名称","考试描述","考试总时间","考试总分数","考试学科","专业名称","班级名称","学生学号","学生名称","学生得分","班级平均分","学生班级排名","专业平均分","专业排名","总平均分","总排名"};
		for(String str : strs){
			cell = row.createCell(index++);
			cell.setCellValue(str);
		}
	}
}
