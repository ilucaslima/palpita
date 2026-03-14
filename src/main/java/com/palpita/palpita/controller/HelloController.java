package com.palpita.palpita.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    public HelloController() {
        System.out.println("HelloController instanciado!");
    }

    @GetMapping("/helloworld")
    public String hello(){
        return "API Palpita Rodando 🔥🚀";
    }
}
