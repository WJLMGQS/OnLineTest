package org.wjlmgqs.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

public class TravelChromosome {

	
	/**
	 *	Map的comparator具有传递性，默认是递增，也具有排序功能
	 *	m的entry遍历是按照key进行
	 */
	@Test
	public void testTreeMapSort(){
		TreeMap<Integer,String> map = new TreeMap<Integer,String>(new Comparator<Integer>() {
			@Override
			public int compare(Integer t1, Integer t2) {
				return t2-t1;//从大到小排列
			}
		});
		map.put(2, "2");
		map.put(1, "1");
		map.put(4, "4");
		map.put(5, "5");
		map.put(4, "4");
		map.put(0, "0");
		TreeMap<Integer,String> m = new TreeMap<Integer,String>(map);
		for(Entry<Integer, String> entry : m.entrySet()){
			System.out.println(entry.getKey());
		}
		System.out.println(map.size());
	}
	
	@Test
	public void testSet2Array(){
		Set<Integer> treeSet = new HashSet<Integer>();
		treeSet.add(3);
		treeSet.add(2);
		treeSet.add(4);
		treeSet.add(1);
		treeSet.add(6);
		List<Integer> list = new ArrayList<Integer>(treeSet);
		Collections.reverse(list);
		
	}

}

