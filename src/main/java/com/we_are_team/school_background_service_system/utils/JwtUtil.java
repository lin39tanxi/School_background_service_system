package com.we_are_team.school_background_service_system.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 生成token
 */
public class JwtUtil {
    public static String createJwt(String secretKey, long ttlMillis, Map<String, Object> claims){
//        指定签名的时候使用的签名算法，也就是header那部分
        SignatureAlgorithm signatureAttribute = SignatureAlgorithm.HS256;
//        生成jwt的时间
        long nowMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(nowMillis);

//        设置jwt的body
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .signWith(signatureAttribute,secretKey.getBytes(StandardCharsets.UTF_8))
                .setExpiration(exp);
        return builder.compact();

    }

    /**
     * 解密token
     * @param token
     * @param secretKey
     * @return
     */
    public static Claims parseJWT(String token, String secretKey){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}

