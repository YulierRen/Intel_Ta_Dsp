package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.dto.aidto.AiDiaryDTO;
import com.lty.www.intel_ta_dsp.dto.aidto.AiSchedule;
import com.lty.www.intel_ta_dsp.dto.aidto.ScheduleGenerateDTO;
import com.lty.www.intel_ta_dsp.mapper.ScheduleMapper;
import com.lty.www.intel_ta_dsp.entity.Schedule;
import com.lty.www.intel_ta_dsp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.lty.www.intel_ta_dsp.datasource.Master;
import com.lty.www.intel_ta_dsp.datasource.Slave;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;

    // ===================== 读方法 =====================
    @Override
    @Slave
    @Cacheable(value = "userSchedules", key = "#studentId")
    public List<Schedule> getScheduleByStudentId(Long studentId) {
        return scheduleMapper.findByUserId(studentId);
    }

    @Override
    @Slave
    @Cacheable(value = "schedule", key = "#id")
    public Schedule getScheduleById(Long id) {
        return scheduleMapper.findById(id);
    }

    @Override
    @Slave
    public List<Schedule> findFromStartToEnd(ScheduleGenerateDTO scheduleGenerateDTO) {
        return scheduleMapper.findFromStartToEnd(scheduleGenerateDTO);
    }

    @Override
    @Slave
    public List<Schedule> findFromStartToEnd(AiDiaryDTO dto) {
        ScheduleGenerateDTO scheduleGenerateDTO = new ScheduleGenerateDTO();
        scheduleGenerateDTO.setUserId(dto.getUserId());
        scheduleGenerateDTO.setStartTime(dto.getStartTime());
        scheduleGenerateDTO.setEndTime(dto.getEndTime());
        return scheduleMapper.findFromStartToEnd(scheduleGenerateDTO);
    }

    // ===================== 写方法 =====================

    @Override
    @Master
    @Caching(evict = {
            @CacheEvict(value = "userSchedules", key = "#schedule.userId"),
            @CacheEvict(value = "scheduleRange", allEntries = true)
    })
    public boolean addSchedule(Schedule schedule) {
        return scheduleMapper.insertSchedule(schedule) > 0;
    }

    @Override
    @Master
    @Caching(evict = {
            @CacheEvict(value = "userSchedules", key = "#schedule.userId"),
            @CacheEvict(value = "scheduleRange", allEntries = true),
            @CacheEvict(value = "schedule", key = "#schedule.id")
    })
    public boolean updateSchedule(Schedule schedule) {
        return scheduleMapper.updateSchedule(schedule) > 0;
    }

    @Override
    @Master
    @Caching(evict = {
            @CacheEvict(value = "userSchedules", allEntries = true),
            @CacheEvict(value = "scheduleRange", allEntries = true),
            @CacheEvict(value = "schedule", key = "#id")
    })
    public boolean deleteSchedule(Long id) {
        return scheduleMapper.deleteSchedule(id) > 0;
    }

    @Override
    @Master
    @Caching(evict = {
            @CacheEvict(value = "userSchedules", key = "#id"),
            @CacheEvict(value = "scheduleRange", allEntries = true)
    })
    public boolean addSchedule(AiSchedule ai, Long id) {
        Schedule schedule = Schedule.builder()
                .userId(id)
                .title(ai.getTitle())
                .description(ai.getDescription())
                .startTime(ai.getStartTime())
                .endTime(ai.getEndTime())
                .location(ai.getLocation())
                .isCompleted(false)
                .build();
        return scheduleMapper.insertSchedule(schedule) > 0;
    }
}
