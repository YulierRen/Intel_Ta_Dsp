package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.entity.Schedule;
import java.util.List;

public interface ScheduleService {
    List<Schedule> getScheduleByStudentId(Long studentId);
}
