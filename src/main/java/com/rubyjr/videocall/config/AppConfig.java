package com.rubyjr.videocall.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:application.properties", "classpath:db.properties", "classpath:config.properties"})
@ComponentScan(basePackages = {
    "com.rubyjr.videocall"
})
public class AppConfig {
}