/**
 * 
 */
package com.github.sfragata.processmanager.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

/**
 * @author Silvio Fragata da Silva - a5014999
 * 
 */
@Component
public class MethodInvoker {

	@SuppressWarnings("rawtypes")
	public void invokeSetMethod(Object obj, String methodName, Object param,
			Class paramType) throws NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		Class partype[] = new Class[1];
		Object arglist[] = new Object[1];
		partype[0] = paramType;
		arglist[0] = param;
		Method method = obj.getClass().getMethod(methodName, partype);
		method.invoke(obj, arglist);
	}

	@SuppressWarnings("rawtypes")
	public Object invokeGetMethod(Object obj, String methodName)
			throws NoSuchMethodException, InvocationTargetException,
			IllegalAccessException {
		Method method = obj.getClass().getMethod(methodName, (Class) null);
		return method.invoke(obj, (Class) null);
	}
}
