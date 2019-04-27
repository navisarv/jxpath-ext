package com.github.navisarv.jxpathext;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ri.JXPathContextFactoryReferenceImpl;

public class CustomJXPathContextFactoryReference extends JXPathContextFactoryReferenceImpl {

	@Override
	public JXPathContext newContext(JXPathContext parentContext, Object contextBean) {
		return new CustomJXPathContextReference(parentContext, contextBean);
	}

}
