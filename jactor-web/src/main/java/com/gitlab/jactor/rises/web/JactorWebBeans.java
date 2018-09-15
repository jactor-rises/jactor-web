package com.gitlab.jactor.rises.web;

import com.gitlab.jactor.rises.commons.datatype.Name;
import com.gitlab.jactor.rises.web.menu.DefaultMenuFacade;
import com.gitlab.jactor.rises.web.menu.Menu;
import com.gitlab.jactor.rises.web.menu.MenuFacade;
import com.gitlab.jactor.rises.web.service.UserRestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static com.gitlab.jactor.rises.web.menu.Menu.aMenu;
import static com.gitlab.jactor.rises.web.menu.MenuItem.aMenuItem;

@Configuration
public class JactorWebBeans {
    private static final String JACTOR_FACADE = "http://localhost:1337/jactor-facade";
    public static final Name MENU_USERS = new Name("users");

    @Bean public MenuFacade menuFacade() {
        return new DefaultMenuFacade(usersMenu());
    }

    private Menu usersMenu() {
        return aMenu()
                .withName(JactorWebBeans.MENU_USERS)
                .add(aMenuItem().withName("menu.users.default")
                        .add(aMenuItem().withName("jactor").withTarget("user?choose=jactor").withDescription("menu.users.jactor.desc"))
                        .add(aMenuItem().withName("tip").withTarget("user?choose=tip").withDescription("menu.users.tip.desc"))
                ).build();
    }

    @Bean public UserRestService userRestService() {
        return new UserRestService(JACTOR_FACADE, new RestTemplate());
    }
}
