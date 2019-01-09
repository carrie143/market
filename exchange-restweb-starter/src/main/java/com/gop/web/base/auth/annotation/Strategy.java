package com.gop.web.base.auth.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gop.web.base.enums.StrategyOperation;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Strategy {
	String authStrategy() default "exe({})" ;
	
	StrategyOperation strategyOperation() default StrategyOperation.AND;
}

