package com.stripe.controllers;

import com.stripe.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/")
    public String index() {
        return "Hello World! ";
    }
}
