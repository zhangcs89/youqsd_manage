package com.wx.youqsd_manage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangchunsheng
 * @version 1.0.0
 * @className JwtConfig
 * @date 2024/8/13   14:33
 * @description TODO
 */
@Component //bean的注入
@ConfigurationProperties(prefix = "sky.jwt")//配置属性类，读取配置文件，在application。yml中配置值
@Data
public class JwtConfig {

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端微信用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}