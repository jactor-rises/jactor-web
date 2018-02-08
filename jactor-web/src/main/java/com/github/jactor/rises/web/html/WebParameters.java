package com.github.jactorrises.web.html;

import org.apache.commons.lang3.Validate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebParameters {
    private static final String LOCALE = "locale";
    private static final String SUBMIT_X = "x";
    private static final String SUBMIT_Y = "y";

    private final Map<String, String> parameterMap = new HashMap<>();

    @SuppressWarnings("unchecked") public WebParameters(HttpServletRequest request) {
        Validate.notNull(request, "The request cannot be null!");

        Map parameters = request.getParameterMap();

        request.getParameterMap().keySet().stream().filter(parameterName -> !isLanguageParameter(parameterName) && !isFormSubmit(parameterName)).forEach(
                parameterName -> parameterMap.put(parameterName.toString(), ((String[]) parameters.get(parameterName))[0])
        );
    }

    private boolean isFormSubmit(Object parameterName) {
        return SUBMIT_X.equals(parameterName) || SUBMIT_Y.equals(parameterName);
    }

    private boolean isLanguageParameter(Object parameterName) {
        return LOCALE.equals(parameterName);
    }

    public List<WebParameter> fetchWebParameters() {
        List<WebParameter> webParameters = new ArrayList<>(parameterMap.size());

        webParameters.addAll(parameterMap.keySet().stream().map(parameterName -> new WebParameter(parameterName, parameterMap.get(parameterName))).collect(Collectors.toList()));

        return webParameters;
    }
}
