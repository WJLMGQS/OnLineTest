package org.wjlmgqs.web.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


public class BeanValidateUtil {

	private static Validator validator = null;
	private static ValidatorFactory defaultValidatorFactory = Validation.buildDefaultValidatorFactory();

	private static void initValidator(){
		if(validator==null){
			validator = defaultValidatorFactory.getValidator();	
		}
	}

	/**
	 * 处理校验结果
	 */
	private static <T> String backResult(Set<ConstraintViolation<T>> constraintViolations) {
		StringBuffer message = new StringBuffer();
		for(ConstraintViolation<T> constraintViolation : constraintViolations){
			message.append("<br/>" +constraintViolation.getMessage());
		}
		String result = message.length()==0?null:message.substring(5);
		return result;
	}
	
//-------------------------------------------------------------------------------------	
	/**
	 * 校验多个Bean的多个属性，返回所有的错误信息，并以\n隔开
	 * 如果没有错误就返回null
	 * 被validatorOnlyVoPropertys替换
	 */
	@Deprecated()
	public static <T> String validatorBeanPropertys(Class<T>[] beanTypes, String[] propertyNames, Object[] values){
		initValidator();
		Set<ConstraintViolation<T>> constraintViolations = null;
		String result = "";
		for(int i=0;i<beanTypes.length;i++){
			constraintViolations = validator.validateValue(beanTypes[i], propertyNames[i], values[i]);
			String message = backResult(constraintViolations);
			if(message!=null){
				result = result +"<br/>"+ message;
			}
			
		}
		return result=="" ? null : result.substring(5);
	}
	/**
	 * 校验某个多个属性，返回所有的错误信息，并以\n隔开
	 * 如果没有错误就返回null
	 */
	public static <T> String validatorOnlyVoPropertys(Class<T> beanTypes, String[] propertyNames, Object[] values){
		initValidator();
		Set<ConstraintViolation<T>> constraintViolations = null;
		String result = "";
		for(int i=0;i<propertyNames.length;i++){
			constraintViolations = validator.validateValue(beanTypes, propertyNames[i], values[i]);
			String message = backResult(constraintViolations);
			if(message!=null){
				result = result +"<br/>"+ message;
			}
			
		}
		return result=="" ? null : result.substring(5);
	}
	/**
	 * 校验某个单个属性，返回所有的错误信息，并以\n隔开
	 * 如果没有错误就返回null
	 */
	public static <T> String validatorVoProperty(Class<T> beanType, String propertyName, Object value){
		initValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validateValue(beanType,propertyName,value);
		return backResult(constraintViolations);
	}
	/**
	 * 校验某个对象，返回所有的错误信息，并以\n隔开
	 * 如果没有错误就返回null
	 */
	public static <T> String validatorVo(T t){
		initValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
		return backResult(constraintViolations);
	}
	
	
}
