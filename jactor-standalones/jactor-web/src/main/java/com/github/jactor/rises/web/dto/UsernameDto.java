package com.github.jactor.rises.web.dto;

import com.github.jactor.rises.web.html.ParameterConstants;
import com.github.jactor.rises.client.datatype.Username;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.WebRequest;

public class UsernameDto {
    private Username username;

    public UsernameDto(WebRequest webRequest) {
        String name = webRequest.getParameter(ParameterConstants.CHOOSE_USER);

        if (StringUtils.isBlank(name)) {
            return;
        }

        this.username = new Username(name);
    }

    public Username getUsername() {
        return username;
    }

    public boolean hasName() {
        return username != null;
    }
}
