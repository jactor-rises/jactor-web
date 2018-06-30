package com.github.jactor.rises.web.mvc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
@PropertySource("classpath:application.properties")
public class CurrentUrlManager {

    public static final String CURRENT_URL = "currentUrl";
    private final String contextPath;

    public CurrentUrlManager(@Value("${server.servlet.context-path}") String contextPath) {
        this.contextPath = contextPath;
    }

    public String init(HttpServletRequest httpServletRequest) {
        String requestURI = httpServletRequest.getRequestURI();
        requestURI = requestURI.replaceAll(contextPath, "");
        String queryString = httpServletRequest.getQueryString();

        if (StringUtils.isBlank(queryString)) {
            return requestURI;
        }

        List<String> parametersWithoutLanguage = stream(queryString.split("&"))
                .filter(param -> !param.startsWith("lang="))
                .collect(Collectors.toList());

        String delimeter = parametersWithoutLanguage.isEmpty() ? "" : "?";

        return requestURI + delimeter + String.join("&", parametersWithoutLanguage);
    }
}
