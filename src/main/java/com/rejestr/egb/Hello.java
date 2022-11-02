package com.rejestr.egb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping("/hello")
    public String seyHello(){
        return "HELLO";
    }

    @GetMapping("/hello2")
    public String seyHello2(){
        return "HELLO2";
    }
}
