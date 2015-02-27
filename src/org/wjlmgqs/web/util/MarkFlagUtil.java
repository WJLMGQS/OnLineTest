package org.wjlmgqs.web.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.wjlmgqs.daomain.ScopeMark;

/**
 * @see 计算班级、专业等平均分，总平均分在数据库完成
 * 		计算班级、专业、总等排名
 */
public class MarkFlagUtil {
	
	private static List<ScopeMark> scopeMarks = null;
	private static Map<String,List<ScopeMark>> classTemp = null;//用来统计同一个班级
	private static Map<String,List<ScopeMark>> careerTemp = null;//用来统计同一个专业
	private static MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);//精度为2，四舍五入
	private static Comparator<ScopeMark> comparator = new Comparator<ScopeMark>() {
		@Override
		public int compare(ScopeMark o1, ScopeMark o2) {
			BigDecimal divide = o2.getStudent_scope().subtract(o1.getStudent_scope(),mc);
			return divide.intValue();
		}
	};
	public static void loadMarkFlag(List<ScopeMark> marks){
		classTemp = new HashMap<String,List<ScopeMark>>();
		careerTemp = new HashMap<String,List<ScopeMark>>();
		scopeMarks = marks;
		startAnalyse();//开始统计并设置结果
	}
	public static void startAnalyse(){
		//对数据按照班级和专业分组
		dataSort();
		//统计各组平均分
		statisticsAVGBySort();
		//对各组进行排序，并设置排名
		setOrderBySort();
		WebLogger.showInfor("分组统计完毕，开始计算平均值。");
	}
	/**
	 * @see 对分组后的数据进行排序
	 */
	private static void setOrderBySort() {
		for(Entry<String, List<ScopeMark>> entry : classTemp.entrySet()){
			List<ScopeMark> value = entry.getValue();
			Collections.sort(value,comparator);
			for(int i=0;i<value.size();i++){
				value.get(i).getMarkFlag().setClass_order(i+1);
			}
		}
		for(Entry<String, List<ScopeMark>> entry : careerTemp.entrySet()){
			List<ScopeMark> value = entry.getValue();
			Collections.sort(value,comparator);
			for(int i=0;i<value.size();i++){
				value.get(i).getMarkFlag().setCareer_order(i+1);
			}
		}
		Collections.sort(scopeMarks,comparator);
		for(int i=0;i<scopeMarks.size();i++){
			scopeMarks.get(i).getMarkFlag().setTotal_order(i+1);
		}
	}
	/**
	 * @see 对数据进行班级和专业的分组
	 */
	private static void dataSort() {
		for(ScopeMark scopeMark : scopeMarks){
			String classCode = scopeMark.getStudent().getClasses().getId()+"";
			String careerCode = scopeMark.getStudent().getClasses().getCareer().getId()+"";
			//各班级分组
			ArrayList<ScopeMark> tempList = null;
			if(classTemp.containsKey(classCode)){
				classTemp.get(classCode).add(scopeMark);
			}else{
				tempList = new ArrayList<ScopeMark>();
				tempList.add(scopeMark);
				classTemp.put(classCode,tempList);
			}
			//各专业分组
			if(careerTemp.containsKey(careerCode)){
				careerTemp.get(careerCode).add(scopeMark);
			}else{
				tempList = new ArrayList<ScopeMark>();
				tempList.add(scopeMark);
				careerTemp.put(careerCode,tempList);
			}
		}
	}
	/**
	 * @see 计算分组后的班级和专业平均分
	 */
	private static void statisticsAVGBySort() {
		for(Entry<String, List<ScopeMark>> entry : classTemp.entrySet()){
			BigDecimal avg = new BigDecimal(0);
			int nums = 0;
			nums = entry.getValue().size();
			for(ScopeMark scope : entry.getValue()){
				avg = avg.add(scope.getStudent_scope());
			}
			avg = avg.divide(new BigDecimal(nums),mc);
			for(ScopeMark scope : entry.getValue()){
				scope.getMarkFlag().setClasses_avg(avg);
			}
		}
		for(Entry<String, List<ScopeMark>> entry : careerTemp.entrySet()){
			BigDecimal avg = new BigDecimal(0);
			int nums = 0;
			nums = entry.getValue().size();
			for(ScopeMark scope : entry.getValue()){
				avg = avg.add(scope.getStudent_scope());
			}
			avg = avg.divide(new BigDecimal(nums),mc);
			for(ScopeMark scope : entry.getValue()){
				scope.getMarkFlag().setCareer_avg(avg);
			}
		}
	}
}
