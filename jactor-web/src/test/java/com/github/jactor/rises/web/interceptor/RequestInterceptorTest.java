package com.github.jactorrises.web.interceptor;

import com.github.jactorrises.web.html.WebParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.jactorrises.web.interceptor.InterceptorValues.ATTRIBUTE_ACTION;
import static com.github.jactorrises.web.interceptor.InterceptorValues.ATTRIBUTE_PARAMETERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("A RequestInterceptor")
class RequestInterceptorTest {
    private RequestInterceptor testRequestInterceptor = new RequestInterceptor();

    @Mock private HttpServletRequest mockedRequest;

    @BeforeEach void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("should set the action attribute on the model")
    @Test void willSetTheActionAttributeOnTheModel() throws Exception {
        when(mockedRequest.getRequestURI()).thenReturn("home.do");

        ModelAndView modelAndView = new ModelAndView();
        testRequestInterceptor.postHandle(mockedRequest, null, null, modelAndView);

        assertThat(modelAndView.getModel().get(ATTRIBUTE_ACTION)).isEqualTo("home.do");
    }

    @DisplayName("shold set the parameter attribute on the model")
    @Test void willSetTheParametersAttributeOnTheModel() throws Exception {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("some", new String[]{"parameter"});

        when(mockedRequest.getParameterMap()).thenReturn(parameterMap);

        ModelAndView modelAndView = new ModelAndView();
        testRequestInterceptor.postHandle(mockedRequest, null, null, modelAndView);

        @SuppressWarnings("unchecked") List<WebParameter> webParams = (List<WebParameter>) modelAndView.getModel().get(ATTRIBUTE_PARAMETERS);

        assertThat(webParams).as("WebParameters").hasSize(1);
        assertThat(webParams.get(0).getName()).as("WebParameter.name").isEqualTo("some");
        assertThat(webParams.get(0).getValue()).as("WebParameter.value").isEqualTo("parameter");
    }
}
