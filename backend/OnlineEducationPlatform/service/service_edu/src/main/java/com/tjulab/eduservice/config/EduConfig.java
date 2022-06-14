package com.tjulab.eduservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.tjulab.eduservice.mapper")
public class EduConfig {
}
