package com.naeva.example.rabbitmq.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/error")
public class ErrorController {
    
    @GetMapping(value="/")
    public String testSimple() {
        log.info("Error");
        return "Error, request has no mapping";
    }
}
