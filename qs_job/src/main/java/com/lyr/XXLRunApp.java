package com.lyr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class XXLRunApp {
    public static void main(String[] args) {
        try{
            SpringApplication.run(XXLRunApp.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("swagger地址 : "+"http://localhost:8888/swagger-ui.html#/");
        }
    }
}
