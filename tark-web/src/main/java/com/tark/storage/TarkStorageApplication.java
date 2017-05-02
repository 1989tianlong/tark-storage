package com.tark.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by jh on 2017/5/2.
 */
@EnableWebMvc
@SpringBootApplication
@MapperScan(basePackages = "com.tark.storage.mapper")
public class TarkStorageApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(TarkStorageApplication.class, args);
    }
}
