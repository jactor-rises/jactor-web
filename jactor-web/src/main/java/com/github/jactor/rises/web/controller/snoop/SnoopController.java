package com.github.jactor.rises.web.controller.snoop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SnoopController {

    @RequestMapping(value = "/snoop.do")
    public void doSnoop() {
    }
}
