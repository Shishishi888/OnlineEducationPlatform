package com.tjulab.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.tjulab"})
@MapperScan("com.tjulab.cmsservice.mapper")
public class CmsBannerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsBannerApplication.class, args);
    }
}
