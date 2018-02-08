package com.github.jactorrises.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.github.jactorrises.web.controller.ControllerValues.VIEW_ABOUT;

@Controller
public class AboutController {

    @RequestMapping(value = VIEW_ABOUT, method = {RequestMethod.GET, RequestMethod.POST})
    public void doAbout() { }
}
