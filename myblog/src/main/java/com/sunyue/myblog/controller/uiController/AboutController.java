package com.sunyue.myblog.controller.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AboutController {
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    private String about() {
        return "ui/about";
    }
}
