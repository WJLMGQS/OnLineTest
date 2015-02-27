package org.wjlmgqs.service.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Test;
import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.web.util.WebLogger;

public class TravelGeneViewInfoUtil {

	
	/**
	 * @see 遍历TreeMap对象，并根据消息类型给出提示
	 */
	public static void travleMap(TreeMap<Integer, Integer> map,String messageType){
		StringBuffer startMessage = new StringBuffer("遍历提示信息：");
		for(Entry<Integer, Integer> entry : map.entrySet()){
			startMessage.append(entry.getValue().toString()+" ");
		}
		WebLogger.showInfor("状态："+messageType+"--------"+startMessage.toString());
	}
	
	/**
	 * @see 根据试题类型染色体的难度对不同基因长度的基因集合进行排序，按照和总体难度的差距从小到大排序
	 */
	public static void comparatorSortList(List<GeneView> geneViews,final int diffculity){
		Collections.sort(geneViews,new Comparator<GeneView>() {
			@Override
			public int compare(GeneView o1, GeneView o2) {
				int index1 = o1.getDifficulty().getIndex()-diffculity;
				int index2 = o2.getDifficulty().getIndex()-diffculity;
				return index2*index2 - index1*index1;//按照和指定难度的差距从小到大排序
			}
		});
	}
	
	@Test
	public void testTravle(){
		TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();
		treeMap.put(1, 1);
		treeMap.put(8, 2);
		treeMap.put(3, 3);
		treeMap.put(4, 4);
		treeMap.put(5, 5);
		treeMap.put(6, 6);
		travleMap(treeMap,"test");
		
		SortedMap<Integer, Integer> subMap = treeMap.subMap(1, 8);
		System.out.println(subMap);
		
		List<String> ob = new ArrayList<String>();
		ob.add("1");
		ob.add("2");
		ob.add("3");
		ob.add("4");
		ob.add("5");
		ob.add("6");
		List<String> subList = ob.subList(0, 2);
		System.out.println(subList);
	}
	
	
	
	
	

}
