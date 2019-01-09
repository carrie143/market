package com.gop.web.base.auth.advice;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.SecurityCodeConst;
import com.gop.exception.AppException;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;
import com.gop.web.base.auth.context.AuthExpressionContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class StratgyAdvice {
	@Autowired
	BeanFactory beanfactory;

	private ExpressionParser parser;

	private StandardEvaluationContext simpleContext;

	@PostConstruct
	public void init() {

		if (parser == null) {
			parser = new SpelExpressionParser();
		}
		if (simpleContext == null) {
			simpleContext = new StandardEvaluationContext(new AuthExpressionContext());
		}
	}

	@Pointcut(value = "@annotation(com.gop.web.base.auth.annotation.Strategys)")
	private void pointcut() {
	}

	@SuppressWarnings("unchecked")
	@Around(value = "pointcut()")
	public Object beforeController(ProceedingJoinPoint pjp) throws Throwable {
		Throwable throwable = null;
		Object o = null;
		Method method = ((MethodSignature) (pjp.getSignature())).getMethod();
		Strategys strategysContext = method.getAnnotation(Strategys.class);
		Strategy[] strategys = strategysContext.strategys();
		Object[] args = pjp.getArgs();
		AuthContext authContext = null;

		for (Object obj : args) {
			if (null == obj) {
				continue;
			}
			if (AuthContext.class.isAssignableFrom(obj.getClass())) {
				authContext = (AuthContext) obj;
				break;
			}
		}
		for (Strategy strategy : strategys) {
			List<List<String>> beansArray = null;
			try {
				beansArray = (List<List<String>>) parser.parseExpression(strategy.authStrategy())
						.getValue(simpleContext);
			} catch (Exception e) {
				log.error("解析表达式异常:{}", e);
				throw new AppException(CommonCodeConst.SERVICE_ERROR);
			}
			
			boolean flag = false;
			for (List<String> beans : beansArray) {
				for (int i = 0; i < beans.size(); i++) {
					AuthStrategy authStrategy = (AuthStrategy) beanfactory.getBean(beans.get(i));
					if (!authStrategy.match(authContext)) {
						break;
					} else {
						authStrategy.pre(authContext);
					}
					if (i == beans.size() - 1) {
						flag = true;
					}
				}
				if (flag == true) {
					break;
				}
			}
			
			if(flag==false)
			{
				throw new AppException(SecurityCodeConst.NO_PERMISSION);
			}
		}
		/*
		 * for (Strategy strategy : strategys) { String beanNames =
		 * strategy.authStrategy(); StrategyOperation operation =
		 * strategy.strategyOperation(); if
		 * (operation.equals(StrategyOperation.AND)) { for (String beanName :
		 * beanNames) { AuthStrategy authStrategy = (AuthStrategy)
		 * beanfactory.getBean(beanName); if (!authStrategy.match(authContext))
		 * { throw new AppException(SecurityCodeConst.NO_PERMISSION); }
		 * authStrategy.pre(authContext); } } else { boolean flag = false; for
		 * (String beanName : beanNames) { AuthStrategy authStrategy =
		 * (AuthStrategy) beanfactory.getBean(beanName); if
		 * (authStrategy.match(authContext)) { authStrategy.pre(authContext);
		 * flag = true; break; } } if (!flag) { throw new
		 * AppException(SecurityCodeConst.NO_PERMISSION); } } }
		 */

		try {
			o = pjp.proceed();
		} catch (Throwable e) {
			throwable = e;
		}
		/*for (Strategy strategy : strategys) {
			String beanNames = strategy.authStrategy();
			
			StrategyOperation operation = strategy.strategyOperation();
			if (operation.equals(StrategyOperation.AND)) {
				for (String beanName : beanNames) {
					AuthStrategy authStrategy = (AuthStrategy) beanfactory.getBean(beanName);
					authStrategy.after(authContext, throwable);
				}
			} else {
				for (String beanName : beanNames) {
					AuthStrategy authStrategy = (AuthStrategy) beanfactory.getBean(beanName);
					if (authStrategy.match(authContext)) {
						authStrategy.after(authContext, throwable);
						break;
					}
				}

			}
		}*/

		for (Strategy strategy : strategys) {
			List<List<String>> beansArray = null;
			try {
				beansArray = (List<List<String>>) parser.parseExpression(strategy.authStrategy())
						.getValue(simpleContext);
			} catch (Exception e) {
				log.error("解析表达式异常:{}", e);
				throw new AppException(CommonCodeConst.SERVICE_ERROR);
			}
			boolean flag = false;
			for (List<String> beans : beansArray) {
				for (int i = 0; i < beans.size(); i++) {
					AuthStrategy authStrategy = (AuthStrategy) beanfactory.getBean(beans.get(i));
					if (!authStrategy.match(authContext)) {
						break;
					} else {
						authStrategy.after(authContext,throwable);
					}
					if (i == beans.size() - 1) {
						flag = true;
					}
				}
				
				if (flag == true) {
					break;
				}
			}
		}
		if (throwable != null) {
			throw throwable;
		}
		return o;
	}

}
