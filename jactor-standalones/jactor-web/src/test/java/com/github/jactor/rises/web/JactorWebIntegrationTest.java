package com.github.jactor.rises.web;

import com.github.jactor.rises.web.controller.AboutController;
import com.github.jactor.rises.web.controller.HomeController;
import com.github.jactor.rises.web.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JactorWebApplication.class)
class JactorWebApplicationIntegrationTest {

    @Resource private HomeController homeController;

    @Resource private AboutController aboutController;

    @Resource private UserController userController;

    @Test void shouldFetchControllersFromSpringFacade() {
        assertThat(homeController).as("HomeController").isNotNull();
        assertThat(aboutController).as("AboutController").isNotNull();
        assertThat(userController).as("UserController").isNotNull();
    }
}
