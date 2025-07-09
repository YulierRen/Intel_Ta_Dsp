package com.lty.www.intel_ta_dsp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFriend {
    private Long userId;
    private Long friendId;
    private LocalDateTime createdAt;
}
