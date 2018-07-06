package com.github.jactor.rises.web.controller;

import com.github.jactor.rises.client.datatype.Username;
import com.github.jactor.rises.client.domain.User;
import com.github.jactor.rises.client.facade.UserFacade;
import com.github.jactor.rises.model.facade.JactorFacade;
import com.github.jactor.rises.model.facade.MenuFacade;
import com.github.jactor.rises.model.facade.menu.MenuItem;
import com.github.jactor.rises.web.dto.UserDto;
import com.github.jactor.rises.web.i18n.MyMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.github.jactor.rises.model.facade.menu.MenuItem.aMenuItem;
import static java.util.Collections.singletonList;

@Controller
public class UserController {

    private final MyMessages myMessages;
    private final UserFacade userFacade;
    private final MenuFacade menuFacade;

    public @Autowired UserController(UserFacade userFacade, MyMessages myMessages, MenuFacade menuFacade) {
        this.myMessages = myMessages;
        this.userFacade = userFacade;
        this.menuFacade = menuFacade;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
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
        Optional<User> user = userFacade.find(new Username(username));
        Map<String, Object> modelMap = modelAndView.getModel();

        if (user.isPresent()) {
            modelMap.put("user", initUserDto(user.get()));
        } else {
            modelMap.put("unknownUser", username);
        }
    }

    private UserDto initUserDto(User user) {
        UserDto userDto = new UserDto(user);
        userDto.fetchDescriptionCode().ifPresent(
                s -> userDto.setFullDescription(myMessages.fetchMessage(s))
        );

        return userDto;
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
