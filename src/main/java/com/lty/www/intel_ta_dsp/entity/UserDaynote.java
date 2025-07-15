package com.lty.www.intel_ta_dsp.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Data
@ToString
@Builder
public class UserDaynote {
    private Long userId;
    private String title;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Date createdAt;
    private Date updatedAt;
}