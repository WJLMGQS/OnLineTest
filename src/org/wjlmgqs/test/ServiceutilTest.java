/**
 **@author wjlmgqs
 **下午11:42:52
 **ServiceutilTest.java
 **org.wjlgmqs.test
 **OnLineTest
 */
package org.wjlmgqs.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.service.util.ServieUtil;

/**
 */
public class ServiceutilTest {

	@Test
	public void testID2ArrayStr(){
		List<GeneView> geneViews = new ArrayList<GeneView>();
		for(int i=0;i<10;i++){
			GeneView geneView = new GeneView();
			geneView.setId(i);
			geneViews.add(geneView);
		}
		String[] str = ServieUtil.ID2ArrayStr(geneViews);
		System.out.println(str);
	}
	
	@Test
	public void testInitArray(){
		int[][] inits = new int[10][10];
		Integer[] ints = new Integer[10];
		for(int i=0;i<10;i++){
			ints[i] = i;
		}
		ServieUtil.initArray(inits, ints);
		for(int i=0;i<inits.length;i++){
			for(int j=0;j<inits[i].length;j++){
				System.out.print(inits[i][j]);;
			}System.out.println();
		}
	}
}
