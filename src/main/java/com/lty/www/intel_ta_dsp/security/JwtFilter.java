package com.lty.www.intel_ta_dsp.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final RedisTemplate<String,Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, java.io.IOException {
        System.out.println("【JwtFilter】请求 URI: " + request.getRequestURI());
        // 在 doFilter 中跳过 /api/auth/** 的请求
        if (request.getRequestURI().startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }


        String header = request.getHeader("Authorization");
        System.out.println("【JwtFilter】Authorization 头: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);


            String username = jwtUtils.getUsernameFromToken(token);
            String redisToken = stringRedisTemplate.opsForValue().get("login:token:" + username);
            if (!token.equals(redisToken)) {
                throw new RuntimeException("Token 已失效，请重新登录");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("用户权限" + userDetails.getAuthorities());
                if (jwtUtils.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}