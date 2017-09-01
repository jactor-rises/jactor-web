package com.github.jactorrises.web;

import com.github.jactorrises.web.controller.AboutController;
import com.github.jactorrises.web.controller.HomeController;
import com.github.jactorrises.web.controller.UserController;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JactorWebApplication.class})
public class JactorWebApplicationIntegrationTest {

    @Resource private HomeController homeController;

    @Resource private AboutController aboutController;

    @Resource private UserController userController;

    @Test public void shouldGetControllers() {
        assertThat(homeController).as("HomeController").isNotNull();
        assertThat(aboutController).as("AboutController").isNotNull();
        assertThat(userController).as("UserController").isNotNull();
    }
}
