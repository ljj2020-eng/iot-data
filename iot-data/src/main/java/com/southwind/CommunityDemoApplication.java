package com.southwind;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.southwind.mapper")
public class CommunityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityDemoApplication.class, args);
    }

}
