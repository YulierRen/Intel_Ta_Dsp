package com.lty.www.intel_ta_dsp.controller;


import com.lty.www.intel_ta_dsp.dto.LoginRequest;
import com.lty.www.intel_ta_dsp.dto.RegisterRequest;
import com.lty.www.intel_ta_dsp.entity.User;

import com.lty.www.intel_ta_dsp.security.CustomUserDetails;
import com.lty.www.intel_ta_dsp.security.JwtUtils;
import com.lty.www.intel_ta_dsp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        System.out.println(request);
        if (userService.findByUsername(request.getUsername())!=null) {
            return ResponseEntity.badRequest().body("用户名已存在");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role("USER")  // 默认赋予 USER 权限
                .build();

        userService.addUser(user);
        return ResponseEntity.ok("注册成功");
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

        // 组装响应数据
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("studentId", user.getId()); // 取 studentId

        return ResponseEntity.ok(response);
    }

}