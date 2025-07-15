package com.lty.www.intel_ta_dsp.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Data
@ToString
@Builder
public class UserDaynoteDTO {
    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
