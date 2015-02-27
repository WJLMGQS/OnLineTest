/**
 **@author wjlmgqs
 **下午5:30:09
 **TempTest.java
 **org.wjlgmqs.test
 **OnLineTest
 */
package org.wjlmgqs.test;

import org.junit.Test;

/**
 */
public class TempTest {

	/**
	 * 测试父引用是否会调用子实现
	 */
	@Test
	public void testClass(){
		AA bb = new BB();
		System.out.println(bb.getA());
	}
	
}

class AA {
	private int a =0;
	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}
}

class BB extends AA{
	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	private int a = 1;
}
