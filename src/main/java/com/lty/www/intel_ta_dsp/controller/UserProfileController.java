package com.lty.www.intel_ta_dsp.controller;


import com.lty.www.intel_ta_dsp.entity.UserProfile;
import com.lty.www.intel_ta_dsp.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userProfile")
@RequiredArgsConstructor
public class UserProfileController {
    // 注入Service层依赖（Lombok的@RequiredArgsConstructor自动生成构造方法）
    private final UserProfileService userProfileService;

    @PostMapping("/myProfile")
    public ResponseEntity<String> findById(@RequestParam Long userId) {
        UserProfile profile = userProfileService.getUserProfileByUserId(userId);
        if (profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到用户档案");
        }
        return ResponseEntity.ok("查询成功：" + profile);
    }

//    /**
//     * 新增用户档案
//     */
//    @PostMapping("/addProfile")
//    public ResponseEntity<String> addUserProfile(@RequestBody UserProfile userProfile) {
//        try {
//            userProfileService.insertUserProfile(userProfile);
//            return ResponseEntity.status(HttpStatus.CREATED).body("用户档案创建成功");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("创建失败：" + e.getMessage());
//        }
//    }

    /**
     * 更新用户档案
     */
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