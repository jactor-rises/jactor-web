package com.gitlab.jactor.rises.web.controller;

import com.gitlab.jactor.rises.commons.datatype.Username;
import com.gitlab.jactor.rises.model.facade.JactorFacade;
import com.gitlab.jactor.rises.model.facade.MenuFacade;
import com.gitlab.jactor.rises.model.facade.UserFacade;
import com.gitlab.jactor.rises.model.facade.menu.MenuItem;
import com.gitlab.jactor.rises.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static com.gitlab.jactor.rises.model.facade.menu.MenuItem.aMenuItem;
import static java.util.Collections.singletonList;

@Controller
public class UserController {

    private final UserFacade userFacade;
    private final MenuFacade menuFacade;

    public @Autowired UserController(UserFacade userFacade, MenuFacade menuFacade) {
        this.userFacade = userFacade;
        this.menuFacade = menuFacade;
    }

    @GetMapping(value = "/user")
    public ModelAndView get(@RequestParam(name = "choose", required = false) String username) {
        ModelAndView modelAndView = new ModelAndView("user");

        if (username != null && !username.equals("")) {
            populateUser(username, modelAndView);
        }

        populateUserMenu(modelAndView);
        populateDefaultUsers(modelAndView);

        return modelAndView;
    }

    private void populateUser(@RequestParam(name = "choose", required = false) String username, ModelAndView modelAndView) {
        var user = userFacade.find(new Username(username));
        Map<String, Object> modelMap = modelAndView.getModel();

        if (user.isPresent()) {
            modelMap.put("user", new UserDto(user.get()));
        } else {
            modelMap.put("unknownUser", username);
        }
    }

    private void populateUserMenu(ModelAndView modelAndView) {
        List<String> usernames = userFacade.findAllUsernames();
        modelAndView.addObject("usersMenu", singletonList(
                aMenuItem()
                        .withName("menu.users.choose")
                        .addAsChildren(usernames)
                        .build()
                )
        );
    }

    private void populateDefaultUsers(ModelAndView modelAndView) {
        List<MenuItem> items = menuFacade.fetchMenuItems(JactorFacade.MENU_USERS);
        modelAndView.addObject("defaultUsers", items);
    }
}
