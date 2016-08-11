package com.furture.react;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * Java接口代理，通过代理把接口的调用转发到JavaScript对象上。
 * */
public class JSInvocationHandler implements InvocationHandler {

	private Object  targetRef;
	
	
	public JSInvocationHandler(Object targetRef) {
		super();
		this.targetRef = targetRef;
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		if (targetRef instanceof JSRef) {
			JSRef jsRef =  (JSRef) targetRef;
			return jsRef.getEngine().call(jsRef, method.getName(), args);
		}else{
			return method.invoke(targetRef, args);
		}
	}



}
