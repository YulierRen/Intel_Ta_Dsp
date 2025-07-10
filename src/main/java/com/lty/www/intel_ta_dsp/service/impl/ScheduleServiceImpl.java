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

    @Override
    public Schedule getScheduleById(Long id) {
        return scheduleMapper.findById(id);
    }

    @Override
    public boolean addSchedule(Schedule schedule) {
        return scheduleMapper.insertSchedule(schedule) > 0;
    }

    @Override
    public boolean updateSchedule(Schedule schedule) {
        return scheduleMapper.updateSchedule(schedule) > 0;
    }

    @Override
    public boolean deleteSchedule(Long id) {
        return scheduleMapper.deleteSchedule(id) > 0;
    }

}
