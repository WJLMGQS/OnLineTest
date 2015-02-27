package org.wjlmgqs.web.util;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class BigDecimalValidator implements ConstraintValidator<BigDecimalValidatorSupport, BigDecimal> {
	private String regex = null;
	@Override
	public void initialize(BigDecimalValidatorSupport constraintAnnotation) {
		regex = constraintAnnotation.regex();
	}
	@Override
	public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
		if(value==null){
			return false;
		}
		String BigStr = value + "";
		if(BigStr.matches(regex)){
			return true;
		}
		return false;
	}

}
