package com.wuyiccc.yuheng.infrastructure.utils;

import com.wuyiccc.yuheng.infrastructure.config.security.YuHengSecurityJwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/7/1 10:26
 */
@Component
@Slf4j
public class JwtUtils {

    private static YuHengSecurityJwtConfig YU_HENG_SECURITY_JWT_CONFIG;

    public static final String ADMIN_PREFIX = "admin";

    public JwtUtils(YuHengSecurityJwtConfig yuHengSecurityJwtConfig) {
        JwtUtils.YU_HENG_SECURITY_JWT_CONFIG = yuHengSecurityJwtConfig;
    }

    public static final String AT = "@";


    public static String createJwtWithPrefix(String prefix, String body, Long expireTimes) {
        return prefix + AT + createJwt(body, expireTimes);
    }


    public static String createJwtWithPrefix(String prefix, String body) {
        return prefix + AT + createJwt(body);
    }

    private static String createJwt(String body, Long expireTimes) {
        return dealJWT(body, expireTimes);
    }


    private static String createJwt(String body) {
        return dealJWT(body, null);
    }

    private static String dealJWT(String body, Long expireTimes) {
        String userKey = YU_HENG_SECURITY_JWT_CONFIG.getKey();

        log.info("jwt key: {}", userKey);
        String base64 = new BASE64Encoder().encode(userKey.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(base64.getBytes());
        String jwt = "";
        if (Objects.isNull(expireTimes)) {
            jwt = generateJwt(body, secretKey);
        } else {
            jwt = generateJwt(body, expireTimes, secretKey);
        }

        log.info("jwt: {}", jwt);
        return jwt;
    }

    private static String generateJwt(String body, SecretKey secretKey) {
        String jwtToken = Jwts.builder()
                .setSubject(body)
                .signWith(secretKey)
                .compact();
        return jwtToken;
    }


    private static String generateJwt(String body, Long expireTimes, SecretKey secretKey) {

        // 定义过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expireTimes);
        String jwtToken = Jwts.builder()
                .setSubject(body)
                .signWith(secretKey)
                .setExpiration(expireDate)
                .compact();
        return jwtToken;
    }


    public static String checkJwt(String jwt) {
        String userKey = YU_HENG_SECURITY_JWT_CONFIG.getKey();
        log.info("jwt key: {}", userKey);

        String base64 = new BASE64Encoder().encode(userKey.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(base64.getBytes());

        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

        // 校验jwt
        Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);

        return jws.getBody().getSubject();
    }
}
