package com.rejestr.egb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;


@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@EnableScheduling
public class EgbApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgbApplication.class, args);
    }

    @Scheduled(fixedDelay = 7200000)
    public void scheduleRun(){
        System.out.println("Current Scheduled Time: " + new Date());
    }

}
