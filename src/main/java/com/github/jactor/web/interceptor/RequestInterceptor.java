package com.github.jactor.web.interceptor;

import static com.github.jactor.web.DatatypesKt.ENGLISH;
import static com.github.jactor.web.DatatypesKt.LANG;
import static com.github.jactor.web.DatatypesKt.NORWEGIAN;
import static com.github.jactor.web.DatatypesKt.THAI;

import com.github.jactor.web.model.CurrentUrlManager;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@PropertySource("classpath:application.properties")
public class RequestInterceptor implements HandlerInterceptor {

  private static final String CHOSEN_VIEW = "chosenView";
  static final String CURRENT_URL = "currentUrl";

  private final String contextPath;

  @Autowired
  public RequestInterceptor(@Value("${server.servlet.context-path}") String contextPath) {
    this.contextPath = contextPath;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView
  ) {
    if (modelAndView != null) {
      CurrentUrlManager currentUrlManager = new CurrentUrlManager(contextPath, request);
      modelAndView.addObject(CHOSEN_VIEW, currentUrlManager.fetchChosenView());
      modelAndView.addObject(CURRENT_URL, currentUrlManager.fetch());

      putAllLanguageAttributes(modelAndView.getModel());
    }
  }

  private void putAllLanguageAttributes(Map<String, Object> model) {
    Locale localeForCurrentThread = LocaleContextHolder.getLocale();

    boolean isEnglish = !localeForCurrentThread.equals(new Locale("no")) && !localeForCurrentThread.equals(new Locale("th"));

    model.put(ENGLISH, isEnglish);
    model.put(NORWEGIAN, localeForCurrentThread.equals(new Locale("no")));
    model.put(THAI, localeForCurrentThread.equals(new Locale("th")));

    model.put(LANG, isEnglish ? "en" : localeForCurrentThread.getLanguage());
  }
}
