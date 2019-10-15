package com.codegym.controller;

import com.codegym.model.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {

    @GetMapping("/")
    public String index() {
        return "/user/index";
    }

    @GetMapping("/user")
    public String user(Principal principal) {
        // Get authenticated user name from Principal
        System.out.println(principal.getName());
        return "/user/user";
    }

    @GetMapping("/admin")
    public String admin() {
        // Get authenticated user name from SecurityContext
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getName());
        return "/user/admin";
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/user/login");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }
}
