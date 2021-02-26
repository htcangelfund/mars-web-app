package com.itcd.angelfund.mars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebNavigationController {
    @RequestMapping(value = {"/", "/report"})
    public String forwardIndex() {
        return "index";
    }
}
