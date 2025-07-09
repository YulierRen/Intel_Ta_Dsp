package com.lty.www.intel_ta_dsp.controller;

import com.lty.www.intel_ta_dsp.entity.Schedule;
import com.lty.www.intel_ta_dsp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 根据ID查询表
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Schedule>> getScheduleByStudentId(@PathVariable Long studentId) {
        System.out.println(studentId);
        List<Schedule> schedules = scheduleService.getScheduleByStudentId(studentId);
        return ResponseEntity.ok(schedules);
    }
}