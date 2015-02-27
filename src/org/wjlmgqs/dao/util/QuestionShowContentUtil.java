package org.wjlmgqs.dao.util;

import org.wjlmgqs.web.util.WebLogger;

public class QuestionShowContentUtil {

	private static String strPeel = "••••••";
	
	public static String showShortContent(String content){
		content = content.trim();
		//更改文本域内容
		if(content.length()>35){
			content = content.substring(0,30)+strPeel;
		}
		WebLogger.showInfor(content);
		return content;
	}
	
}
