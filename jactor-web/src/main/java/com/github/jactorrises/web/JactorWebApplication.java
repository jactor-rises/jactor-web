package com.github.jactorrises.web;

import com.github.jactorrises.web.menu.DefaultMenuFacade;
import com.github.jactorrises.web.menu.Menu;
import com.github.jactorrises.web.menu.MenuFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.github.jactorrises.web.menu.Menu.aMenu;
import static com.github.jactorrises.web.menu.MenuItem.aMenuItem;

@SpringBootApplication
public class JactorWebApplication {

    @Bean(name = "jactor.web.menuFacade") public MenuFacade menuFacade() {
        return new DefaultMenuFacade(mainMenu(), personMenu());
    }

    private Menu mainMenu() {
        return aMenu()
                .withName("main")
                .add(aMenuItem()
                        .withName("menu.main.home").withDescription("menu.main.home.desc").withTarget("home.do")
                        .add(aMenuItem().withName("menu.main.jactor").withDescription("menu.main.jactor.desc").withTarget("home.do?choose=jactor"))
                        .add(aMenuItem().withName("menu.main.tip").withDescription("menu.main.tip.desc").withTarget("home.do?chooose=tip"))
                ).build();
    }

    private Menu personMenu() {
        return aMenu()
                .withName("person")
                .add(aMenuItem().withName("menu.person.about").withDescription("menu.person.about.desc").withTarget("about.do"))
                .build();
    }

    public static void main(String... args) {
        SpringApplication.run(JactorWebApplication.class, args);
    }
}