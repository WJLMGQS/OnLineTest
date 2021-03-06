package org.wjlmgqs.web.controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestUnit;
import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.service.impl.SingleServiceImp;
import org.wjlmgqs.web.util.WebLogger;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SingleId2Singe extends HttpServlet {

	/**
	 * @see 根据指定的single Id 返回Single
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String singleId = request.getParameter("opvalue").trim();
		PrintWriter out = response.getWriter();
		String responseContext = "{}";
		//设置要输出的日期默认格式
		final String  dataPartten = "yyyy-MM-dd HH:mm:ss";
		//设置要输出的属性        
		final String regFormat = "|SA|SB|SC|SD|result|image|analyse|content|difficulty|createTime|";        
		//创建一个带过滤条件的gson对象,过滤日期格式和输出属性
        Gson gson = new GsonBuilder().setDateFormat(dataPartten).setExclusionStrategies(new ExclusionStrategy() {
             /** 设置要过滤的属性 */
            @Override
            public boolean shouldSkipField(FieldAttributes attr) {
                boolean b = false;//定义false，返回true，表示不输出属性
                //当遇到要过滤的类时
                if(attr.getDeclaringClass()== TestUnit.class || attr.getDeclaringClass()== Single.class) {//这里表示字段声明时所在的类：attr.getDeclaringClass()
                    b = regFormat.contains("|" + attr.getName() + "|");
                } 
                return !b;
            }
            /** 设置要过滤的类*/
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                //这里，如果返回true就表示此类要过滤，否则就输出
                return false;
            }
        }).create();
        Single single = null;
		if (singleId == null) {
		} else {
			try {
				int single_id = Integer.parseInt(singleId);
				SingleServiceImp singleServiceImp = new SingleServiceImp();
				//返回所有记录
				single = singleServiceImp.getSingleById(single_id,(Teacher)request.getSession().getAttribute("professionTeacher"));
			} catch (NumberFormatException e) {
				WebLogger.showInfor("传入的知识点参数错误！");
				e.printStackTrace();
			}
		}
		responseContext = gson.toJson(single);
		//替换难度显示符号
		try {
			JSONObject obj = new JSONObject(responseContext);
			String index = obj.getString("difficulty");
			obj.put("difficulty",QuestionDifficultyType.valueOf(index).getName());
			responseContext = obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		WebLogger.showInfor("----responseContext:"+responseContext);
		out.print(responseContext);
		out.flush();
		out.close();
		return;
	}
}
