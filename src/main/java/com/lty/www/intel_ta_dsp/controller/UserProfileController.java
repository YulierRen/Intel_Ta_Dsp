package com.lty.www.intel_ta_dsp.controller;


import com.lty.www.intel_ta_dsp.entity.UserProfile;
import com.lty.www.intel_ta_dsp.service.AliOssService;
import com.lty.www.intel_ta_dsp.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/userProfile")
@RequiredArgsConstructor
public class UserProfileController {
    // 注入Service层依赖（Lombok的@RequiredArgsConstructor自动生成构造方法）
    private final UserProfileService userProfileService;
    @Autowired
    private AliOssService ossService;

    @PostMapping("/myProfile")
    public ResponseEntity<UserProfile> findById(@RequestParam Long userId) {
        UserProfile profile = userProfileService.getUserProfileByUserId(userId);
        if (profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserProfile());
        }
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/upload/avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            return ossService.upload(file);
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfile userProfile) {
        // 检查用户是否存在
        UserProfile existingProfile = userProfileService.getUserProfileByUserId(userProfile.getUserId());
        if (existingProfile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到用户档案");
        }
        try {
            userProfileService.updateUserProfile(userProfile);
            return ResponseEntity.ok("用户档案更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败：" + e.getMessage());
        }
    }
}