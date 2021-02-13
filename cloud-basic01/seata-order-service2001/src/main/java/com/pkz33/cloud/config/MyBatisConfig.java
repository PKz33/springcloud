package com.pkz33.cloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.pkz33.cloud.dao"})
public class MyBatisConfig {
}
