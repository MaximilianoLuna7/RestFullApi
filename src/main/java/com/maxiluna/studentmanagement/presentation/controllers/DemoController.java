package com.maxiluna.studentmanagement.presentation.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @PostMapping("/secure")
    public String secureEndPoint() {
        return "You're authorized";
    }
}
