package com.practise;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.practise.realz.mapper")
public class ArchitectPractiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchitectPractiseApplication.class, args);
    }
}
