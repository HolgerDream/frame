package com.gener.core.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SystemInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = -8076343053982997328L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
	return invocation.invoke();

    }
}
