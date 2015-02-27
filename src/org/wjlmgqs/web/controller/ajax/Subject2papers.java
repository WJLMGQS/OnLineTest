/**
 **@author wjlmgqs
 **下午12:25:50
 **FillBlankId2FillBlank.java
 **org.wjlmgqs.web.controller.ajax
 **OnLineTest20.4
 */
package org.wjlmgqs.web.controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.enums.TestPaperBulidType;
import org.wjlmgqs.enums.TestPaperUseType;
import org.wjlmgqs.service.impl.TestPaperServiceImp;
import org.wjlmgqs.web.util.WebLogger;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @see 加载试卷的属性：教师编号、试卷名称、日期(创建、修改)、各试题数量|分值、试卷难度、性质(组卷、使用)、科目
 * 					id/name/createTime/updateTime/[single:singleMark|multiple:multipleMark|judge:judgeMark|fillBlank:fillBlankMark]]/difficulty/[bulidType|useType]|subject	
 * 		只加载已经发布的试卷，使用类型(根据传递到参数，因为以后模拟考试也用到)
 */
public class Subject2papers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String finishTime = request.getParameter("finishTime").trim();
		String totalMark = request.getParameter("totalMark").trim();
		String subjectId = request.getParameter("opvalue").trim();
		PrintWriter out = response.getWriter();
		String responseContext = "{}";
		// 设置要输出的日期默认格式
		final String dataPartten = "yy/MM/dd HH:mm:ss";
		// 设置要输出的属性
		final String regFormat = "|id|name|createTime|updateTime|difficulty|bulidType|useType|totalMark|teacher|";
		final String tFormat = "|subject|userId|";
		final String sFormat = "|code|";
		// 创建一个带过滤条件的gson对象,过滤日期格式和输出属性
		Gson gson = new GsonBuilder().setDateFormat(dataPartten).setExclusionStrategies(new ExclusionStrategy() {
					/** 设置要过滤的属性 */
					@Override
					public boolean shouldSkipField(FieldAttributes attr) {
						boolean b = false;// 定义false，返回true，表示不输出属性
						// 当遇到要过滤的类时
						if (attr.getDeclaringClass() == TestPaper.class) {// 这里表示字段声明时所在的类：attr.getDeclaringClass()
							b = regFormat.contains("|" + attr.getName() + "|");
						}else if(attr.getDeclaringClass() == Teacher.class){
							b = tFormat.contains("|" + attr.getName() + "|");
						}else if(attr.getDeclaringClass() == Subject.class){
							b = sFormat.contains("|" + attr.getName() + "|");
						}
						return !b;
					}

					/** 设置要过滤的类 */
					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						// 这里，如果返回true就表示此类要过滤，否则就输出
						return false;
					}
				}).create();
		List<TestPaper> testPapers = null;
        Map<String,List<TestPaper>> map = new HashMap<String,List<TestPaper>>();
		if (subjectId == null) {
		} else {
			try {
				int subject_id = Integer.parseInt(subjectId);
				int finishtime = Integer.parseInt(finishTime);
				int totalmark = Integer.parseInt(totalMark);
				TestPaperServiceImp testPaperServiceImp = new TestPaperServiceImp();
				// 返回所有教师试卷代理对象记录
				testPapers = testPaperServiceImp.getTestPapersBySubjectId_UserType_Teacher(subject_id,finishtime,totalmark);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				WebLogger.showInfor("错误提示：传入的学科或使用类型参数错误！");
			}
		}
		map.put("aaData",testPapers);
		responseContext = gson.toJson(map);
		//替换每道试题难度显示符号
		try {
			JSONObject jobj = new JSONObject(responseContext);
			JSONArray obj = jobj.getJSONArray("aaData");
			for(int i=0;i<obj.length();i++){
				JSONObject _obj = obj.getJSONObject(i);
				_obj.put("difficulty", QuestionDifficultyType.valueOf(_obj.getString("difficulty")).getName());;
				_obj.put("bulidType", TestPaperBulidType.valueOf(_obj.getString("bulidType")).getName());;
				_obj.put("useType", TestPaperUseType.valueOf(_obj.getString("useType")).getName());;
			}
			responseContext = jobj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		WebLogger.showInfor("----responseContext:" + responseContext);
		out.print(responseContext);
		out.flush();
		out.close();
		return;
	}	
	
	/**
	 * 数据分析
	{"aaData":
		[{
		"bulidType":"AUTO",
		"createTime":"13/04/13_18:24:25",
		"difficulty":"EASY",
		"fillBlankMark":2.0,
		"id":"20130413182320291",
		"judgeMark":1.0,
		"multipleMark":3.0,
		"name":"Oracle",
		"singleMark":1.0,
		"updateTime":"13/04/13_18:24:25",
		"useType":"ALL"
		}]}

	 * */
	
}
