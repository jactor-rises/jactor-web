package com.gitlab.jactor.rises.web;

import com.gitlab.jactor.rises.commons.framework.SpringBeanNames;
import com.gitlab.jactor.rises.model.facade.JactorFacade;
import com.gitlab.jactor.rises.web.menu.MenuFacade;
import com.gitlab.jactor.rises.web.menu.DefaultMenuFacade;
import com.gitlab.jactor.rises.web.menu.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.gitlab.jactor.rises.model.facade.JactorFacade.MENU_USERS;
import static com.gitlab.jactor.rises.web.menu.Menu.aMenu;
import static com.gitlab.jactor.rises.web.menu.MenuItem.aMenuItem;
import static java.util.Arrays.stream;

@SpringBootApplication
@Import(JactorFacade.class)
public class JactorWeb implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JactorWeb.class);

    public @Bean CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> inspect(applicationContext, args);
    }

    public @Bean MenuFacade menuFacade() {
        return new DefaultMenuFacade(usersMenu());
    }

    private Menu usersMenu() {
        return aMenu()
                .withName(MENU_USERS)
                .add(aMenuItem().withName("menu.users.default")
                        .add(aMenuItem().withName("jactor").withTarget("user?choose=jactor").withDescription("menu.users.jactor.desc"))
                        .add(aMenuItem().withName("tip").withTarget("user?choose=tip").withDescription("menu.users.tip.desc"))
                ).build();
    }

    private void inspect(ApplicationContext applicationContext, String[] args) {
        if (LOGGER.isDebugEnabled()) {
            boolean noArgs = args == null || args.length == 0;
            String arguments = noArgs ? "without arguments" : "with arguments: " + String.join(" ", args);

            LOGGER.debug("Starting jactor-web {}", arguments);

            SpringBeanNames springBeanNames = new SpringBeanNames();
            stream(applicationContext.getBeanDefinitionNames()).sorted().forEach(springBeanNames::add);

            LOGGER.debug("Available beans:");
            springBeanNames.getBeanNames().stream().map(name -> "- " + name).forEach(LOGGER::debug);

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Available spring beans:");
                springBeanNames.getNamesOfSpringBeans().stream().map(name -> "- " + name).forEach(LOGGER::trace);
            }
        }
    }

    public static void main(String... args) {
        SpringApplication.run(JactorWeb.class, args);
    }
}
