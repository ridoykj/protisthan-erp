package com.itbd.protisthan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiRestController {
    @GetMapping("test")
    public String hello(){
        return "hello";
    }
}
