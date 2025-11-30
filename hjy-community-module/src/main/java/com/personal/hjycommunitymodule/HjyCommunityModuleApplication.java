package com.personal.hjycommunitymodule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.personal.hjycommunitymodule.**.mapper")
public class HjyCommunityModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(HjyCommunityModuleApplication.class, args);
        System.out.println("启动测试");
    }

}
