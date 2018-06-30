package com.github.jactor.rises.web.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("An UrlWithoutLanguage")
@PropertySource("classpath:application.properties")
class CurrentUrlManagerTest {

    private @Autowired CurrentUrlManager currentUrlManager;
    private @MockBean HttpServletRequest httpServletRequestMock;
    private @Value("${server.servlet.context-path}") String contextPath;

    @BeforeEach void initMocking() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("should init currentUrl and attach it to the model")
    @Test void shouldInitCurrentUrl() {
        when(httpServletRequestMock.getRequestURI()).thenReturn("/user");
        when(httpServletRequestMock.getQueryString()).thenReturn("choose=jactor");

        assertThat(currentUrlManager.init(httpServletRequestMock)).isEqualTo("/user?choose=jactor");
    }

    @DisplayName("should not add query string to currentUrl if query string is blank")
    @Test void shouldNotAddQueryStringToCurrentUrl() {
        when(httpServletRequestMock.getRequestURI()).thenReturn("/user");
        when(httpServletRequestMock.getQueryString()).thenReturn("");

        assertThat(currentUrlManager.init(httpServletRequestMock)).isEqualTo("/user");
    }

    @DisplayName("should not add parameter called lang")
    @Test void shouldNotAddLangParameter() {
        when(httpServletRequestMock.getRequestURI()).thenReturn("/home");
        when(httpServletRequestMock.getQueryString()).thenReturn("lang=en");

        String languageParam = currentUrlManager.init(httpServletRequestMock);

        when(httpServletRequestMock.getRequestURI()).thenReturn("/user");
        when(httpServletRequestMock.getQueryString()).thenReturn("lang=no&choose=tip");

        String langAndOtherParam = currentUrlManager.init(httpServletRequestMock);

        assertAll(
                () -> assertThat(languageParam).as("only language param").isEqualTo("/home"),
                () -> assertThat(langAndOtherParam).as("one language and one user params").isEqualTo("/user?choose=tip")
        );
    }

    @DisplayName("should not add context-path to current url")
    @Test void shouldNotAddContextPath() {
        when(httpServletRequestMock.getRequestURI()).thenReturn(contextPath + "/home");

        assertThat(currentUrlManager.init(httpServletRequestMock)).isEqualTo("/home");
    }
}
