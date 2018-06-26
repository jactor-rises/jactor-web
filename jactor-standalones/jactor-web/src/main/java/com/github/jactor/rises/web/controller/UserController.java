package com.github.jactor.rises.web.controller;

import com.github.jactor.rises.client.datatype.Username;
import com.github.jactor.rises.client.domain.User;
import com.github.jactor.rises.client.facade.UserFacade;
import com.github.jactor.rises.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    static final String ATTRIBUTE_USER = "user";
    static final String ATTRIBUTE_USER_UNKNOWN = "";
    static final String REQUEST_USER = "user";

    private final UserFacade userFacade;

    public @Autowired UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String get(ModelMap modelMap, @RequestParam(name = REQUEST_USER, required = false) String username) {

        if (username != null) {
            Optional<User> user = userFacade.find(new Username(username));

            if (user.isPresent()) {
                modelMap.put(ATTRIBUTE_USER, new UserDto(user.get()));
            } else {
                modelMap.put(ATTRIBUTE_USER_UNKNOWN, username);
            }
        }

        return "user";
    }
}
