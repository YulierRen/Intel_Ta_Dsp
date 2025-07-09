package com.lty.www.intel_ta_dsp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;


@Component
public class JwtUtils {

    private final String SECRET = "你的32位以上随机字符串密钥，必须足够长1234"; // 最少32字节，建议更复杂

    private Key key;

    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 1天

    @PostConstruct
    public void init() {
        // 用于初始化 key，只初始化一次
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // 添加角色信息到 payload
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // token无效
            return false;
        }
    }
}
