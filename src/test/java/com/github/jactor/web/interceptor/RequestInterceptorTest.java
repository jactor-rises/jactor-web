package com.github.jactor.web.interceptor;

import static com.github.jactor.web.DatatypesKt.ENGLISH;
import static com.github.jactor.web.DatatypesKt.LANG;
import static com.github.jactor.web.DatatypesKt.NORWEGIAN;
import static com.github.jactor.web.DatatypesKt.THAI;
import static com.github.jactor.web.interceptor.RequestInterceptor.CURRENT_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.ModelAndView;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("A RequestInterceptor")
class RequestInterceptorTest {

  @Autowired
  private RequestInterceptor requestInterceptorToTest;
  @MockBean
  private HttpServletRequest httpServletRequestMock;

  @Test
  @DisplayName("should add information to the model")
  void shouldAddInformationToTheModel() {
    LocaleContextHolder.setLocale(new Locale("no"));
    ModelAndView modelAndView = new ModelAndView();

    when(httpServletRequestMock.getRequestURI()).thenReturn("/somewhere");
    when(httpServletRequestMock.getQueryString()).thenReturn("out=there&lang=something&another=param");

    requestInterceptorToTest.postHandle(httpServletRequestMock, null, null, modelAndView);

    Map<String, Object> model = modelAndView.getModel();

    assertAll(
        () -> assertThat(model.get(CURRENT_URL)).as(CURRENT_URL).isEqualTo("/somewhere?out=there&another=param"),
        () -> assertThat(model.get(ENGLISH)).as(ENGLISH).isEqualTo(false),
        () -> assertThat(model.get(NORWEGIAN)).as(NORWEGIAN).isEqualTo(true),
        () -> assertThat(model.get(THAI)).as(THAI).isEqualTo(false),
        () -> assertThat(model.get(LANG)).isEqualTo("no")
    );
  }
}
