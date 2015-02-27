package org.wjlmgqs.web.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=BigDecimalValidator.class)
@Documented
public @interface BigDecimalValidatorSupport {

	String message() default "{org.wjlmgqs.bigDecimalValidator}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; 
    String regex() default ".";
}
