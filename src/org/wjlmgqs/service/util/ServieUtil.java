package org.wjlmgqs.service.util;

import java.util.ArrayList;
import java.util.List;

import org.wjlmgqs.daomain.GeneView;

public class ServieUtil {

	/**
	 * 将List的id转换成[id]形式
	 */
	public static String[] ID2ArrayStr(List<GeneView> geneViews){
		List<String> list = new ArrayList<String>();
		String[] strs = new String[list.size()];
		for(GeneView geneView : geneViews){
			list.add(geneView.getId()+"");
		}
		return list.toArray(strs);
	}
	
	
	/**
	 *根据基因数和染色体长度，计算需要的染色体数量
	 */
	public static int firgurePopulationSize(int geneNum,int length){
		int value = 0;
		
		return value ;
	}
	
	public static void initArray(int[][] inits,Integer[] indexs){
		for(int i=0;i<inits.length;i++){
			for(int j=0;j<indexs[i];j++){
				inits[i][j] = 1;
			}
		}
	}
	
	
}
