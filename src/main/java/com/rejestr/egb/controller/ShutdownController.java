package com.rejestr.egb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShutdownController {
    @Autowired
    private ApplicationContext context;

    public void initiateAppShutdown(int returnCode){
        SpringApplication.exit(context, ()-> returnCode);
    }

    @RequestMapping("/shutdown")
    public void shutdown(){
        initiateAppShutdown(0);
    }

}


