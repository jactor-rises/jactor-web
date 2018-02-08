package com.github.jactor.rises.web;

import com.github.jactor.rises.web.controller.AboutController;
import com.github.jactor.rises.web.controller.HomeController;
import com.github.jactor.rises.web.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JactorWebApplication.class)
public class JactorWebApplicationIntegrationTest {

    @Resource private HomeController homeController;

    @Resource private AboutController aboutController;

    @Resource private UserController userController;

    @Test public void shouldFetchControllersFromSpringFacade() {
        assertThat(homeController).as("HomeController").isNotNull();
        assertThat(aboutController).as("AboutController").isNotNull();
        assertThat(userController).as("UserController").isNotNull();
    }
}
