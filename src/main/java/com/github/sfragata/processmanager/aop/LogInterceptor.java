/**
 * 
 */
package com.github.sfragata.processmanager.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author silvio.silva
 */
@Aspect
@Component
public class LogInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

	@Around("execution(* com.github.sfragata.processmanager.manager.impl..*(..))")
	public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
		String serviceName = new StringBuilder(pjp.getTarget().getClass().getName()).append(".")
				.append(pjp.getSignature().getName()).toString();
		if (logger.isDebugEnabled()) {
			logger.debug("Beginning method: {}", serviceName);
		}
		StopWatch watch = new StopWatch("Profiling");
		Object retVal = null;
		watch.start(pjp.toShortString());
		try {
			retVal = pjp.proceed();
		} catch (Throwable e) {
			logger.error(serviceName, e);
			throw e;
		} finally {
			watch.stop();
			if (logger.isDebugEnabled()) {
				logger.debug("Ending method: {}: {}", serviceName, watch.shortSummary());
			}
		}
		return retVal;
	}
}
