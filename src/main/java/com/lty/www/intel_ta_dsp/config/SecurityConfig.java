package com.lty.www.intel_ta_dsp.config;

import com.lty.www.intel_ta_dsp.security.JwtFilter;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)  // 启用方法级安全注解 @PreAuthorize
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    // 密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 认证管理器
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @PostConstruct
    public void enableAsyncSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
    // 安全过滤链配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // ✅ 开启跨域
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 允许所有人访问登录/注册接口
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/avatar/**").permitAll()
                        .requestMatchers("/api/diary/generateDiaryStream").permitAll()
                        .requestMatchers("/error").permitAll()
                        // Swagger 相关（开发环境开放）
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 用户管理接口（仅 ADMIN 可访问）
                        .requestMatchers(
                                "/api/user/changeRole",
                                "/api/user/delete",
                                "/api/user/deleteById/**"
                        ).hasRole("ADMIN")

                        // 用户信息更新（USER 和 ADMIN 均可）
                        .requestMatchers(
                                "/api/user/changeUsername",
                                "/api/user/changePassword",
                                "/api/user/add",
                                "/api/user/allUser",
                                "/api/user/find"
                        ).hasAnyRole("USER", "ADMIN")

                        // 日程管理
                        .requestMatchers("/api/schedule/**").hasAnyRole("USER", "ADMIN")

                        // 个人资料管理
                        .requestMatchers("/api/userProfile/**").hasAnyRole("USER", "ADMIN")

                        // AI 智能生成（USER 和 ADMIN 可访问）
                        .requestMatchers("/api/ai/**").hasAnyRole("USER", "ADMIN")

                        // 日志功能
                        .requestMatchers("/api/userDiary/**").hasAnyRole("USER", "ADMIN")

                        // 好友功能
                        .requestMatchers("/api/userFriend/**", "/api/friendRequest/**").hasAnyRole("USER", "ADMIN")

                        // 其他所有请求需要认证
                        .anyRequest().authenticated()
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(login -> login.disable()) // ❌ 禁用默认表单登录
                .httpBasic(httpBasic -> httpBasic.disable())  // ❌ 禁用 HTTP Basic 认证（否则浏览器弹窗登录）
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://47.105.37.144");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true); // 允许携带 Cookie 或 Authorization

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}