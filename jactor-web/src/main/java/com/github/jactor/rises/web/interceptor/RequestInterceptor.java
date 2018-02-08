package com.github.jactor.rises.web.interceptor;

import com.github.jactor.rises.web.html.WebParameters;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A {@link HandlerInterceptorAdapter} that will give the action attribute to the the requested view and enable any of its views to be able to send an html form action to the same
 * request. It will also put the requested parameters on the {@link ModelAndView}.
 */
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Override public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) {
        modelAndView.addObject(InterceptorValues.ATTRIBUTE_ACTION, request.getRequestURI());
        modelAndView.addObject(InterceptorValues.ATTRIBUTE_PARAMETERS, new WebParameters(request).fetchWebParameters());
    }
}
