/**
 **@author wjlmgqs
 **下午10:03:16
 **PoiDemo.java
 **org.wjlmgqs.test
 **OnLineTest
 */
package org.wjlmgqs.test;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.wjlmgqs.daomain.ScopeMark;
import org.wjlmgqs.daomain.Student;

public class PoiDemo {
	public static List<ScopeMark> loadMarkList(){
		List<ScopeMark> scopeMarks = new ArrayList<ScopeMark>();
		ScopeMark scopeMark = null;
		Student student = null;
		for(int i=0;i<10;i++){
			student = new Student();
			student.setUserId("student_"+i);
			scopeMark = new ScopeMark(student, null, new BigDecimal(i), new BigDecimal(i), "testPaper_"+i, new Date());
			scopeMarks.add(scopeMark);
		}
		return scopeMarks;
	}
	@Test
	public void createDemo(){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("学生考试成绩表");
		int rows_index = 0;//行标
		short cols_index = 0;//列标
		HSSFRow row = sheet.createRow(rows_index++);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		row.setRowStyle(style);
		buildRowTitle(row, cols_index);
		List<ScopeMark> list = loadMarkList();
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(rows_index++);
			ScopeMark scopeMark = (ScopeMark) list.get(i);
			buildRowContent(row,scopeMark);
		}
		try {
			FileOutputStream fout = new FileOutputStream("D:/students.xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	private void buildRowTitle(HSSFRow row, short index) {
		HSSFCell cell = row.createCell(index++);
		String[] strs = {"考试名称","考试描述","考试总时间","考试总分数","考试学科","专业名称","班级名称","学生学号","学生名称","学生得分","班级平均分","学生班级排名","专业平均分","专业排名","总平均分","总排名"};
		for(String str : strs){
			cell = row.createCell(index++);
			cell.setCellValue(str);
		}
	}
}
