package com.wx.youqsd_manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(
        basePackages = {"com.wx.youqsd_manage.mappper"}
)
public class YouqsdManageApplication {

    public static void main(String[] args) {

        SpringApplication.run(YouqsdManageApplication.class, args);
        System.out.println("\nYouqsdManageApplication Server 启动成功：");
    }

}
