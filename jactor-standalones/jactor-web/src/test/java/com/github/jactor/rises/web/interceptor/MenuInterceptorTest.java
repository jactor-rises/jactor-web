package com.github.jactor.rises.web.interceptor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("A MenuInterceptor")
class MenuInterceptorTest {

    private @Autowired MenuInterceptor menuInterceptorToTest;

    @DisplayName("should intercept http requests and use the menu facade according to request")
    @Test void whenHandlingHttpRequestTheAwareMenuItemsAreGathered() {
        ModelAndView modelAndView = new ModelAndView();
        HttpServletRequest mockedHttpServletRequest = mock(HttpServletRequest.class);

        when(mockedHttpServletRequest.getRequestURI()).thenReturn("uri");
        menuInterceptorToTest.postHandle(mockedHttpServletRequest, null, null, modelAndView);
        Map<String, Object> model = modelAndView.getModel();

        assertAll(
                () -> assertThat(model.get(MenuInterceptor.ATTRIBUTE_MAIN_ITEMS)).as("The aware main items should be present").isNotNull(),
                () -> assertThat(model.get(MenuInterceptor.ATTRIBUTE_PERSON_ITEMS)).as("The aware persons items should be present").isNotNull()
        );
    }
}
