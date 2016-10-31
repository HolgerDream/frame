package com.frame.core;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
@Controller("systemInterceptor")
@Scope("prototype")
public class SystemInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = -8076343053982997328L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
	return invocation.invoke();

    }
}
