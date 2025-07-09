package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.mapper.ScheduleMapper;
import com.lty.www.intel_ta_dsp.entity.Schedule;
import com.lty.www.intel_ta_dsp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;

    @Override
    public List<Schedule> getScheduleByStudentId(Long studentId) {
        return scheduleMapper.findByUserId(studentId);
    }

}
