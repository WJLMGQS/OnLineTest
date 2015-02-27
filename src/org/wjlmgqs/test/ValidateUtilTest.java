/**
 **@author wjlmgqs
 **下午1:12:30
 **ValidateUtilTest.java
 **org.wjlgmqs.test
 **OnLineTest
 */
package org.wjlmgqs.test;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.junit.Test;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.BigDecimalValidatorSupport;

public class ValidateUtilTest {

	@Test
	public void testValidateDemo(){
		B b = new B();
		b.birthday = new Date();
		b.name = ";";
		b.setPassword(null);
		b.setBig(new BigDecimal("1.5"));
		System.out.println(b.getBig());
		String result = BeanValidateUtil.validatorOnlyVoPropertys(B.class,
				new String[]{"birthday","name","password","big"}, 
				new Object[]{b.birthday,b.name,b.getPassword(),b.getBig()});
		System.out.println(result);
	}
	@Test
	public void testMatcherBlank(){
		String str = " j ";
		String pattern = ".*\\S+.*";
		System.out.println(str.matches(pattern));;
	}
}

class A{
	public BigDecimal getBig() {
		return big;
	}
	public void setBig(BigDecimal big) {
		this.big = big;
	}
	@Pattern(message="姓名格式不正确",regexp="\\w{3}")
	protected String name;
	@NotNull
	protected Date birthday;
	@BigDecimalValidatorSupport(message="别乱来啊",regex="(^0\\.50*$)|(^[1-9]+\\d*(\\.[05])?0*$)")
	private BigDecimal big = null;
}

class B extends A{
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull(message="不能空啊")
	private String password;
	
} 