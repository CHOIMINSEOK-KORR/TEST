package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeminiController {

    @GetMapping("/gemini")
    public String gemini() {
        return "Hello from Gemini!";
    }
}
