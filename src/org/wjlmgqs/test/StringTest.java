package org.wjlmgqs.test;

import java.util.Arrays;

import org.junit.Test;

public class StringTest {

	@Test
	public void testStringArray(){
		String[] strs = "sdfsdn,dfs,ew,wew,sf".split(",");
		System.out.println(Arrays.toString(strs));
	}
	
}
