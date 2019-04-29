package com.github.navisarv.jxpathext;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathInvalidSyntaxException;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.jxpath.ri.EvalContext;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.axes.InitialContext;
import org.apache.commons.jxpath.ri.axes.RootContext;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.beans.BeanPointer;
import org.apache.commons.jxpath.ri.model.beans.BeanPropertyPointer;
import org.apache.commons.jxpath.ri.model.beans.NullPointer;

public class CustomJXPathContextReference extends JXPathContextReferenceImpl {

	protected CustomJXPathContextReference(JXPathContext parentContext, Object contextBean) {
		super(parentContext, contextBean);
	}

	@Override
	public Pointer createPathAndSetValue(String xpath, Expression expr, Object value) {
		return Objects.nonNull(value) ? super.createPathAndSetValue(xpath, expr, value) : null;
	}

	@Override
	public Object getValue(String xpath, Expression expr) {
		Object computeValue = expr
				.computeValue(new InitialContext(new RootContext(this, (NodePointer) getContextPointer())));
		Pointer pointer = computeValue instanceof Pointer ? (Pointer) computeValue
				: computeValue instanceof EvalContext ? ((EvalContext) computeValue).getSingleNodePointer() : null;
		if (pointer instanceof NodePointer && !((NodePointer) pointer).isActual()) {
			customizeEval(xpath, (NodePointer) pointer);
		}
		return super.getValue(xpath, expr);
	}

	private void customizeEval(String xpath, NodePointer pointer) throws JXPathInvalidSyntaxException {
		QName name = pointer.getName();
		try {
			NodePointer parent = pointer.getImmediateParentPointer();
			if (Objects.isNull(parent) || parent instanceof NullPointer || !parent.isActual()) {
				customizeEval(xpath, parent);
			} else if (parent instanceof BeanPointer || parent instanceof BeanPropertyPointer) {
				Object bseVal = parent instanceof BeanPointer ? ((BeanPointer) parent).getBaseValue()
						: parent instanceof BeanPropertyPointer ? ((BeanPropertyPointer) parent).getBean() : null;
				if(parent instanceof BeanPropertyPointer) {
					((BeanPropertyPointer) parent).getPropertyNames();
				}
				if (Objects.nonNull(bseVal)) {
					if (Objects.nonNull(name)) {
						new PropertyUtilsBean().getProperty(bseVal, name.getName());
					} else if (pointer.asPath().contains(parent.asPath())) {
						throw new JXPathInvalidSyntaxException(
								String.format("Invalid predicate<%s%s> in xpath<%s>", parent.getName().getName(),
										pointer.asPath().substring(parent.asPath().length()), xpath));
					}
				}
			}

		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
			throw new JXPathNotFoundException(
					String.format("Property \"%s\" not exist in xpath \"%s\"", name.getName(), xpath));
		}

	}

}
