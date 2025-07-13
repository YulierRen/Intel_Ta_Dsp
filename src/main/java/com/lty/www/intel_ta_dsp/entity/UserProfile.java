package com.lty.www.intel_ta_dsp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    private Long userId;              // 主键，关联 user 表
    private String nickname;          // 昵称
    private String avatarUrl;         // 头像 URL
    private Gender gender;            // 性别枚举
    private LocalDate birthday;       // 出生日期
    private String bio;               // 个人简介

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;  // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;  // 更新时间

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
