package com.redhat.sso.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;
import java.util.Optional;

@RequestMapping("/index")
@RestController
public class IndexController {

    @GetMapping
    public ModelAndView index(ModelAndView modelAndView, Principal principal) {
        final String message = "Hello" + Optional.ofNullable(principal)
                .map(Principal::getName)
                .orElse("Spring Security");
        modelAndView.addObject("message", message);
        modelAndView.setViewName("index");
        return modelAndView;
    }

}
