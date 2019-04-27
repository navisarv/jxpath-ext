package com.github.navisarv.test;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathInvalidSyntaxException;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.navisarv.jxpathext.CustomJXPathContext;
import com.github.navisarv.model.Employee;

public class CustomJXPathTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testCustomJXPathContext() {
		Employee emp = new Employee();
		CustomJXPathContext.newContext(emp).getValue("/address/zip");
	}

	@Test
	public void testJXPathContext() {
		exception.expect(JXPathNotFoundException.class);
		Employee emp = new Employee();
		JXPathContext.newContext(emp).getValue("/address/zip");
	}

	@Test
	public void testJXPathContextWithUnknownProperty() {
		exception.expect(JXPathInvalidSyntaxException.class);
		exception.expectMessage("Property \"address1\" not exist in xpath \"/address1\"");
		Employee emp = new Employee();
		CustomJXPathContext.newContext(emp).getValue("/address1");
	}

}
