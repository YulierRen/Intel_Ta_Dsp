package com.lty.www.intel_ta_dsp.dto;

import lombok.Data;

@Data
public class UserDiaryDTO {
    private Long userId;
    private String title;
    private String content;
    private Boolean isPublic=false; // 默认公开
    private Long id;                   // 日记ID
}