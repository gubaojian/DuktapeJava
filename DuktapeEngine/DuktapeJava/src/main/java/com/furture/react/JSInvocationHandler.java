package com.furture.react;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JSInvocationHandler implements InvocationHandler {

	private Object  targetRef;
	
	
	public JSInvocationHandler(Object targetRef) {
		super();
		this.targetRef = targetRef;
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		if (method.getName().equals("toString")) { //art 模式，此句不能debug
			return toString();
		}
		if (targetRef instanceof JSRef) {
			JSRef jsRef =  (JSRef) targetRef;
			return jsRef.getEngine().call(jsRef, method.getName(), args);
		}else{
			return method.invoke(targetRef, args);
		}
	}

}
