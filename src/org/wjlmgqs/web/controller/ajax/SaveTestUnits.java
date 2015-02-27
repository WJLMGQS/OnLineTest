/**
 **@author wjlmgqs
 **下午1:54:43
 **SaveTestUnits.java
 **org.wjlmgqs.web.controller.ajax
 **OnLineTest
 */
package org.wjlmgqs.web.controller.ajax;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.FillBlank;
import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.Multiple;
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.web.util.WebLogger;

public class SaveTestUnits extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		//接受参数
		String _type = request.getParameter("type");
		String _results = request.getParameter("results");
		_results = URLDecoder.decode(_results,"UTF-8");
		int type = -1;
		String[] results = null;
		//校验并解析参数
		try {//利用异常机制省去检测null
			type = Integer.parseInt(_type);
			if(type<0 || type >3){
				showMessage = "错误信息：试题类型不存在！";
			}
			results = _results.split(",",-1);
			if(results.length<=0){
				showMessage = "错误信息：试题解析错误！";
			}
		} catch (Exception e) {
			type = -1;
			showMessage = "错误信息：试题解析错误！";
			e.printStackTrace();
		}
		//获得当前考试的试卷，判断试题类型
		TestPaper testPaper = (TestPaper) request.getSession().getAttribute("testPaper");
		if(testPaper==null){
			showMessage = "错误信息：当前考生尚未参加任何考试！";
		}
		//更新试题到session中
		if(showMessage==null){
			try {
				switch(type){
					case 0:
						doSingle(results,testPaper);
						break;
					case 1:
						doMultiple(results,testPaper);
						break;
					case 2:
						doJudge(results,testPaper);
						break;
					case 3:
						doFillBlank(results,testPaper);
						break;
				}
			} catch (Exception e) {
				showMessage = "错误信息：试题数据异常，保存失败！";
				e.printStackTrace();
			}
			request.getSession().setAttribute("testPaper",testPaper);//更新试卷
			showMessage = "提示信息：试题保存成功！";
		}
		WebLogger.showInfor(showMessage);
		response.getWriter().write(showMessage);
	}
	
	
	private void doSingle(String[] results,TestPaper testPaper){
		List<Single> singles = testPaper.getSingles();
		for(int i=0;i<results.length;i++){
			Single single = singles.get(i);
			single.setResult(results[i]);
		}
	}
	private void doMultiple(String[] results,TestPaper testPaper){
		List<Multiple> multiples = testPaper.getMultiples();
		for(int i=0;i<results.length;i++){
			Multiple multiple = multiples.get(i);
			int lastIndex = results[i].lastIndexOf("░");//去除最后一个░
			String[] split = results[i].substring(0, lastIndex).split("░",-1);
			multiple.setResults(split);
		}
	}
	private void doJudge(String[] results,TestPaper testPaper){
		List<Judge> judges = testPaper.getJudges();
		for(int i=0;i<results.length;i++){
			Judge judge = judges.get(i);
			judge.setResult(results[i]);
		}
	}
	private void doFillBlank(String[] results,TestPaper testPaper){
		List<FillBlank> fillBlanks = testPaper.getFillBlanks();
		for(int i=0;i<results.length;i++){
			FillBlank fillBlank = fillBlanks.get(i);
			int lastIndex = results[i].lastIndexOf("░");//去除最后一个░
			String[] split = results[i].substring(0, lastIndex).split("░",-1);
			fillBlank.setResults(split);
		}
	}

}
