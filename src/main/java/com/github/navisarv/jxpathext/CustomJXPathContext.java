package com.github.navisarv.jxpathext;

import org.apache.commons.jxpath.JXPathContext;

public abstract class CustomJXPathContext extends JXPathContext {

	protected CustomJXPathContext(JXPathContext parentContext, Object contextBean) {
		super(parentContext, contextBean);
	}

	public static JXPathContext newContext(Object contextBean) {
		JXPathContext newContext = new CustomJXPathContextFactoryReference().newContext(null, contextBean);
		newContext.setLenient(true);
		return newContext;
	}
	
	public static JXPathContext newContext(JXPathContext parent, Object contextBean) {
		return new CustomJXPathContextFactoryReference().newContext(parent, contextBean);
	}
}
