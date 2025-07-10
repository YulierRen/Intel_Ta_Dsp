package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.entity.Schedule;
import java.util.List;

public interface ScheduleService {

    // 查询：根据用户ID（学生ID）获取所有日程
    List<Schedule> getScheduleByStudentId(Long studentId);

    // 查询：根据日程ID获取单条记录
    Schedule getScheduleById(Long id);

    // 添加新的日程
    boolean addSchedule(Schedule schedule);

    // 修改已有日程
    boolean updateSchedule(Schedule schedule);

    // 删除日程
    boolean deleteSchedule(Long id);
}
