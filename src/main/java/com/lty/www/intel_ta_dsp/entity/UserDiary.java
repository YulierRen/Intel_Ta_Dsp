package com.lty.www.intel_ta_dsp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDiary {
    private Long id;                   // 日记ID
    private Long userId;              // 所属用户ID
    private String title;             // 日记标题
    private String content;           // 日记正文
    private Boolean isPublic;         // 是否公开（true=公开，false=私密）
    private LocalDateTime createdAt;  // 创建时间
    private LocalDateTime updatedAt;  // 更新时间
}
