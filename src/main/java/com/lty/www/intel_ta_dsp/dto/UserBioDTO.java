package com.lty.www.intel_ta_dsp.dto;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
@Builder
public class UserBioDTO {
    private Long userId;
    private Date startTime;
    private Date endTime;
}
