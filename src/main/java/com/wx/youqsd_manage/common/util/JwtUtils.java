package com.wx.youqsd_manage.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhangchunsheng
 * @version 1.0.0
 * @className JwtUtil
 * @date 2024/8/13   14:02
 * @description TODO
 */
@Slf4j
public class JwtUtils {

    public static final long DEFAULT_TTL = 30 * 24 * 60 * 60 * 1000L; // 30天

    public static final String DEFAULT_PLAIN_TEXT = "dbmzlh";

    /**
     * 生成UUID,也可以为雪花算法
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成token令牌,数据为默认的
     * @return
     */
    public static String generateJWT() {
        return generateJWT(DEFAULT_PLAIN_TEXT, DEFAULT_TTL);
    }

    /**
     * 传入JSON对象生成令牌
     * @param subject
     * @return
     */
    public static String generateJWT(String subject) {
        return generateJWT(subject, DEFAULT_TTL);
    }

    /**
     * token 生成器
     * @param subject 信息
     * @param ttlMillis 有效时间
     * @return 令牌
     */
    public static String generateJWT(String subject, Long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey(); // 生成适用于 HMAC 的密钥

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = DEFAULT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(generateUUID()) //唯一ID
                .setSubject(subject) //JSON对象
                .setIssuer("dabaimao") //签发人
                .setIssuedAt(now) //签发时间
                .signWith(signatureAlgorithm, secretKey) // 使用密钥进行签名
                .setExpiration(expDate)
                .compact();
    }

    /**
     * 使用AES算法生成公私钥
     * @return
     */
    public static SecretKey generalKey()
    {
        byte[] encodedKey = Base64.getDecoder().decode(DEFAULT_PLAIN_TEXT.replace("\r\n", ""));
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析token令牌
     * @param jwt 令牌
     * @return 对象
     * @throws Exception
     */
    public static Claims analysisJWT(String jwt) throws Exception
    {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static void main(String[] args) throws Exception {
        String jwtToken = generateJWT("大白猫真厉害", DEFAULT_TTL);
        System.out.println("登录成功生成的token: " + jwtToken);
        //也可以网页解析token，网址https://jwt.io
        System.out.println("解析token得到的数据: "+analysisJWT(jwtToken).toString());
    }


}
