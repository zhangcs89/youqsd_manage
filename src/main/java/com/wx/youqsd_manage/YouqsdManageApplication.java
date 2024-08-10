package com.wx.youqsd_manage;

import com.wx.youqsd_manage.service.impl.ShopServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(
        basePackages = {"com.wx.youqsd_manage.mappper"}
)
public class YouqsdManageApplication {

    private static final Logger logger = LoggerFactory.getLogger(YouqsdManageApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(YouqsdManageApplication.class, args);

        System.out.println("\nYouqsdManageApplication Server 启动成功：");


    }

}
