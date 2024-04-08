package com.s3practice.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthenticationController {


    @GetMapping({"/home", "/"})
    public String index() {
        return "home";
    }

    @GetMapping({"/list-products"})
    public String list() {
        return "list-products";
    }

}
