package com.lty.www.intel_ta_dsp.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor(force = true)  // 确保有无参构造
@AllArgsConstructor                // 确保有全参构造，供 Builder 使用
@ToString
public class Schedule {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private String location;
    private Boolean isCompleted;
    private Date createdAt;
    private Date updatedAt;
}
