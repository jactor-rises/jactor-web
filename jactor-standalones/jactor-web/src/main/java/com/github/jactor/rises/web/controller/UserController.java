package com.github.jactor.rises.web.controller;

import com.github.jactor.rises.client.datatype.Username;
import com.github.jactor.rises.client.domain.User;
import com.github.jactor.rises.client.facade.UserFacade;
import com.github.jactor.rises.web.dto.UserDto;
import com.github.jactor.rises.web.i18n.MyMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    private final MyMessages myMessages;
    private final UserFacade userFacade;

    public @Autowired UserController(UserFacade userFacade, MyMessages myMessages) {
        this.myMessages = myMessages;
        this.userFacade = userFacade;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView get(@RequestParam(name = "choose", required = false) String username) {
        ModelAndView modelAndView = new ModelAndView("user");

        if (username != null) {
            Optional<User> user = userFacade.find(new Username(username));
            Map<String, Object> modelMap = modelAndView.getModel();

            if (user.isPresent()) {
                modelMap.put("user", initUserDto(user.get()));
            } else {
                modelMap.put("unknownUser", username);
            }
        }

        return modelAndView;
    }

    private UserDto initUserDto(User user) {
        UserDto userDto = new UserDto(user);
        userDto.fetchDescription().ifPresent(
                s -> userDto.setFullDescription(myMessages.fetchMessage(s))
        );

        return userDto;
    }
}
