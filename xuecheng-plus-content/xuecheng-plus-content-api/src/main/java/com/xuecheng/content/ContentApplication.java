package com.xuecheng.content;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@EnableSwagger2Doc
@SpringBootApplication
@ComponentScan(basePackages = {"com.xuecheng.base","com.xuecheng.content"})
public class ContentApplication {

    public static void main(String[] args) {

        SpringApplication.run(ContentApplication.class, args);
    }

}
