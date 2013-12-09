/**
 * 
 */
package br.com.sfragata.processmanager.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author silvio.silva
 */
@Aspect
@Component
public class LogInterceptor {

	private static Log logger = LogFactory.getLog(LogInterceptor.class);

	@Around("execution(* *..sfragata.processmanager.manager.impl.*.*(..))")
	public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
		String serviceName = new StringBuilder(pjp.getTarget().getClass()
				.getName()).append(".").append(pjp.getSignature().getName())
				.toString();
		if (logger.isDebugEnabled()) {
			logger.debug(new StringBuilder("Beginning method: ")
					.append(serviceName));
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
				logger.debug(new StringBuilder("Ending method: ")
						.append(serviceName).append(": ")
						.append(watch.shortSummary()).toString());
			}
		}
		return retVal;
	}
}
