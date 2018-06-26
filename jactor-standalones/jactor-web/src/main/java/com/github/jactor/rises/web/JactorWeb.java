package com.github.jactor.rises.web;

import com.github.jactor.rises.commons.framework.SpringBeanNames;
import com.github.jactor.rises.model.facade.JactorFacade;
import com.github.jactor.rises.web.menu.DefaultMenuFacade;
import com.github.jactor.rises.web.menu.Menu;
import com.github.jactor.rises.web.menu.MenuFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.github.jactor.rises.web.menu.Menu.aMenu;
import static com.github.jactor.rises.web.menu.MenuItem.aMenuItem;
import static java.util.Arrays.stream;

@SpringBootApplication
@Import(JactorFacade.class)
public class JactorWeb implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JactorWeb.class);

    @Bean(name = "jactor.web.menuFacade") public MenuFacade menuFacade() {
        return new DefaultMenuFacade(mainMenu(), personMenu());
    }

    private Menu mainMenu() {
        return aMenu()
                .withName("main")
                .add(aMenuItem()
                        .withName("menu.main.home").withDescription("menu.main.home.desc").withTarget("home")
                        .add(aMenuItem().withName("menu.main.jactor").withDescription("menu.main.jactor.desc").withTarget("user?user=jactor"))
                        .add(aMenuItem().withName("menu.main.tip").withDescription("menu.main.tip.desc").withTarget("user?user=tip"))
                ).build();
    }

    private Menu personMenu() {
        return aMenu()
                .withName("person")
                .add(aMenuItem().withName("menu.person.about").withDescription("menu.person.about.desc").withTarget("about"))
                .build();
    }

    public @Bean CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> inspect(applicationContext, args);
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
