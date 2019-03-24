package com.github.jactor.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutController {

    @GetMapping(value = "/about")
    public ModelAndView get() {
        return new ModelAndView("about");
    }
}
