package com.lty.www.intel_ta_dsp.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
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
