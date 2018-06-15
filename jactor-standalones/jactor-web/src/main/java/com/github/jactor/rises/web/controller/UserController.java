package com.github.jactor.rises.web.controller;

import com.github.jactor.rises.client.datatype.Username;
import com.github.jactor.rises.client.facade.UserFacade;
import com.github.jactor.rises.web.dto.UserDto;
import com.github.jactor.rises.web.dto.UsernameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import static com.github.jactor.rises.web.controller.ControllerValues.ATTRIBUTE_USER;
import static com.github.jactor.rises.web.controller.ControllerValues.VIEW_USER;

@Controller
public class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @RequestMapping(value = VIEW_USER, method = {RequestMethod.GET, RequestMethod.POST})
    public void doUser(ModelMap modelMap, WebRequest webRequest) {
        resolveUser(modelMap, webRequest);
    }

    private void resolveUser(ModelMap modelMap, WebRequest webRequest) {
        UsernameDto usernameDto = new UsernameDto(webRequest);

        if (!usernameDto.hasName()) {
            return;
        }

        Username username = usernameDto.getUsername();
        userFacade.find(username)
                .ifPresent(user -> modelMap.put(ATTRIBUTE_USER, new UserDto(user)));
    }
}
