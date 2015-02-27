/**
 **@author wjlmgqs
 **上午12:45:06
 **DateTest.java
 **org.wjlmgqs.test
 **OnLineTest
 */
package org.wjlmgqs.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 */
public class DateTest {

	@Test
	public void testDateFormat() throws ParseException{
		String date = "2013-3-2";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date parse = dateFormat.parse(date);
		System.out.println();
	}
}
