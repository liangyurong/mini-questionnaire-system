package com.lyr.fdfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FdfsRunApp {
    public static void main(String[] args) {
        SpringApplication.run(FdfsRunApp.class,args);
        System.out.println("项目端口："+"8803");
        System.out.println("swagger访问："+"http://localhost:8803/swagger-ui.html");
    }
}
