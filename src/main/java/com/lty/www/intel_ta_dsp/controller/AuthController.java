package com.lty.www.intel_ta_dsp.controller;


import com.lty.www.intel_ta_dsp.dto.LoginRequest;
import com.lty.www.intel_ta_dsp.dto.RegisterRequest;
import com.lty.www.intel_ta_dsp.entity.User;

import com.lty.www.intel_ta_dsp.security.CustomUserDetails;
import com.lty.www.intel_ta_dsp.security.JwtUtils;
import com.lty.www.intel_ta_dsp.service.UserService;
import com.lty.www.intel_ta_dsp.utils.RedisLockUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RedisLockUtil redisLockUtil;
    private final RedisTemplate<String,Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String username = request.getUsername();
        String lockKey = "register:lock:" + username;

        // 加锁，锁 10 秒
        String lockValue = redisLockUtil.tryLock(lockKey, 10);
        if (lockValue == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("注册请求正在处理中，请稍后再试");
        }

        try {
            if (userService.findByUsername(username) != null) {
                return ResponseEntity.badRequest().body("用户名已存在");
            }

            User user = User.builder()
                    .username(username)
                    .password(request.getPassword())
                    .role("USER")
                    .build();

            userService.addUser(user);
            return ResponseEntity.ok("注册成功");

        } finally {
            // 释放锁
            redisLockUtil.unlock(lockKey, lockValue);
        }
    }


    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );


        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        String token = jwtUtils.generateToken(user.getUsername(),user.getRole());
        System.out.println(token);
        String redisKey = "login:token:" + user.getUsername();
        stringRedisTemplate.opsForValue().set(redisKey, token, 30, TimeUnit.MINUTES);


        // 组装响应数据
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("studentId", user.getId()); // 取 studentId

        return ResponseEntity.ok(response);
    }

}