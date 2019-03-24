package com.github.jactor.web.controller;

import com.github.jactor.web.Technology;
import com.github.jactor.web.i18n.MyMessages;
import com.github.jactor.web.model.HomePageModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

  private static final String HOME_VIEW = "home";

  private final MyMessages myMessages;

  @Autowired
  public HomeController(MyMessages myMessages) {
    this.myMessages = myMessages;
  }

  @GetMapping(value = {"/", HOME_VIEW})
  public ModelAndView get() {
    return new ModelAndView(HOME_VIEW)
        .addObject("homepage", new HomePageModel(
                List.of(
                    myMessages.fetchMessage("page.home.paragraph.a"),
                    myMessages.fetchMessage("page.home.paragraph.b"),
                    myMessages.fetchMessage("page.home.paragraph.c")
                ),
                List.of(
                    new Technology("Maven", "http://maven.apache.org", myMessages.fetchMessage("page.home.tech.maven")),
                    new Technology("Kotlin", "https://kotlinlang.org", myMessages.fetchMessage("page.home.tech.kotlin")),
                    new Technology(
                        "Spring Boot", "https://spring.io/projects/spring-boot", myMessages.fetchMessage("page.home.tech.springboot")
                    ),
                    new Technology("Thymeleaf", "https://www.thymeleaf.org", myMessages.fetchMessage("page.home.tech.thymeleaf")),
                    new Technology("Junit", "https://junit.org/junit5/", myMessages.fetchMessage("page.home.tech.junit")),
                    new Technology("Mockito", "http://site.mockito.org", myMessages.fetchMessage("page.home.tech.mockito")),
                    new Technology(
                        "AssertJ", "https://joel-costigliola.github.io/assertj/", myMessages.fetchMessage("page.home.tech.assertj")
                    ),
                    new Technology("Git", "https://git-scm.com", myMessages.fetchMessage("page.home.tech.git"))
                )
            )
        );
  }
}
