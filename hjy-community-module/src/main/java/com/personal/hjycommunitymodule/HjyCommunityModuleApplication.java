package com.personal.hjycommunitymodule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.personal.hjycommunitymodule.**.mapper")
public class HjyCommunityModuleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(HjyCommunityModuleApplication.class, args);
        System.out.println("启动测试");
    }

}
