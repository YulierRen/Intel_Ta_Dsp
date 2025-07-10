package com.lty.www.intel_ta_dsp.mapper;

import com.lty.www.intel_ta_dsp.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    Schedule findById(Long id);

    List<Schedule> findByUserId(Long userId);

    int insertSchedule(Schedule schedule);

    int updateSchedule(Schedule schedule);

    int deleteSchedule(Long id);
}
