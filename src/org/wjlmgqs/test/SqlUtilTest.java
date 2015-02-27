/**
 **@author wjlmgqs
 **下午10:41:52
 **SqlUtilTest.java
 **org.wjlgmqs.test
 **OnLineTest
 */
package org.wjlmgqs.test;

import org.junit.Test;
import org.wjlmgqs.dao.util.SQLUtil;

/**
 */
public class SqlUtilTest {
	
	@Test
	public void test1(){
		new SQLUtil().dealIds2Or(new String[]{"1","2","3"},"id");
	}
}
