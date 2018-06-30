package com.github.jactor.rises.web.interceptor;

import com.github.jactor.rises.web.mvc.CurrentUrlManager;
import com.github.jactor.rises.web.mvc.LanguageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private final CurrentUrlManager currentUrlManager;
    private final LanguageManager languageManager;

    public @Autowired RequestInterceptor(CurrentUrlManager currentUrlManager, LanguageManager languageManager) {
        this.currentUrlManager = currentUrlManager;
        this.languageManager = languageManager;
    }

    @Override public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) {
        if (modelAndView != null) {
            modelAndView.addObject(CurrentUrlManager.CURRENT_URL, currentUrlManager.init(request));
            languageManager.putAllLanguageAttributes(modelAndView.getModel());
        }
    }
}
