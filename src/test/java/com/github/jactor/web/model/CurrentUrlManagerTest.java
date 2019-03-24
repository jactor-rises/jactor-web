package com.github.jactor.web.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@DisplayName("A CurrentUrlManager")
class CurrentUrlManagerTest {

  private CurrentUrlManager currentUrlManager;

  @MockBean
  private HttpServletRequest httpServletRequestMock;
  @Value("${server.servlet.context-path}")
  private String contextPath;

  @BeforeEach
  void initCurrentUrlManager() {
    currentUrlManager = new CurrentUrlManager(contextPath, httpServletRequestMock);
  }

  @Test
  @DisplayName("should fetch currentUrl and attach it to the model")
  void shouldInitCurrentUrl() {
    when(httpServletRequestMock.getRequestURI()).thenReturn("/user");
    when(httpServletRequestMock.getQueryString()).thenReturn("choose=jactor");

    assertThat(currentUrlManager.fetch()).isEqualTo("/user?choose=jactor");
  }

  @Test
  @DisplayName("should not add query string to currentUrl if query string is blank")
  void shouldNotAddQueryStringToCurrentUrl() {
    when(httpServletRequestMock.getRequestURI()).thenReturn("/user");
    when(httpServletRequestMock.getQueryString()).thenReturn("");

    assertThat(currentUrlManager.fetch()).isEqualTo("/user");
  }

  @Test
  @DisplayName("should not add parameter called lang")
  void shouldNotAddLangParameter() {
    when(httpServletRequestMock.getRequestURI()).thenReturn("/home");
    when(httpServletRequestMock.getQueryString()).thenReturn("lang=en");

    String languageParam = currentUrlManager.fetch();

    when(httpServletRequestMock.getRequestURI()).thenReturn("/user");
    when(httpServletRequestMock.getQueryString()).thenReturn("lang=no&choose=tip");

    String langAndOtherParam = currentUrlManager.fetch();

    assertAll(
        () -> assertThat(languageParam).as("only language param").isEqualTo("/home"),
        () -> assertThat(langAndOtherParam).as("one language and one user params").isEqualTo("/user?choose=tip")
    );
  }

  @Test
  @DisplayName("should not add context-path to current url")
  void shouldNotAddContextPath() {
    when(httpServletRequestMock.getRequestURI()).thenReturn(contextPath + "/home");

    assertThat(currentUrlManager.fetch()).isEqualTo("/home");
  }

  @Test
  @DisplayName("should not add centext-path to the view name")
  void shouldNotAddViewNameToTheModel() {
    when(httpServletRequestMock.getRequestURI()).thenReturn(contextPath + "/someView");

    assertThat(currentUrlManager.fetchChosenView()).isEqualTo("someView");
  }
}
