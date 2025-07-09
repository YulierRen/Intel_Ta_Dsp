package com.lty.www.intel_ta_dsp.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FriendRequest {
    private Long senderId;
    private Long receiverId;
    private String status; // PENDING / ACCEPTED / REJECTED
    private LocalDateTime createdAt;
}
