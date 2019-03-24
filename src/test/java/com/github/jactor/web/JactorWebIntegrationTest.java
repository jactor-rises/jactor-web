package com.github.jactor.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.jactor.web.controller.AboutController;
import com.github.jactor.web.controller.HomeController;
import com.github.jactor.web.controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JactorWeb.class)
class JactorWebIntegrationTest {

  @Autowired
  private HomeController homeController;

  @Autowired
  private AboutController aboutController;

  @Autowired
  private UserController userController;

  @Test
  @DisplayName("should fetch controllers from spring context")
  void shouldFetchControllersFromSpringContext() {
    assertThat(homeController).as("HomeController").isNotNull();
    assertThat(aboutController).as("AboutController").isNotNull();
    assertThat(userController).as("UserController").isNotNull();
  }
}
