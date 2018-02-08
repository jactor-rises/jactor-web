package com.github.jactor.rises.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.github.jactor.rises.web.controller.ControllerValues.VIEW_HOME;

@Controller
public class HomeController {

    @RequestMapping(value = VIEW_HOME, method = {RequestMethod.GET, RequestMethod.POST})
    public void doHome() { }
}
