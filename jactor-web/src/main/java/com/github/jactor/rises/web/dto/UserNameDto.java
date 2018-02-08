package com.github.jactor.rises.web.dto;

import com.github.jactor.rises.web.html.ParameterConstants;
import com.github.jactorrises.client.datatype.UserName;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.WebRequest;

public class UserNameDto {
    private UserName userName;

    public UserNameDto(WebRequest webRequest) {
        String name = webRequest.getParameter(ParameterConstants.CHOOSE_USER);

        if (StringUtils.isBlank(name)) {
            return;
        }

        this.userName = new UserName(name);
    }

    public UserName getUserName() {
        return userName;
    }

    public boolean hasName() {
        return userName != null;
    }
}
