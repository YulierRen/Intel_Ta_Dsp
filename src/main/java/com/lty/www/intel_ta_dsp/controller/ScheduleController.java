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

    /**
     * 查询：根据用户ID（学生ID）获取所有日程
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Schedule>> getScheduleByStudentId(@PathVariable Long studentId) {
        List<Schedule> schedules = scheduleService.getScheduleByStudentId(studentId);
        return ResponseEntity.ok(schedules);
    }

    /**
     * 查询：根据日程ID获取单条记录
     */
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        if (schedule != null) {
            return ResponseEntity.ok(schedule);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 添加新的日程
     */
    @PostMapping("/add")
    public ResponseEntity<String> addSchedule(@RequestBody Schedule schedule) {
        System.out.println(schedule);
        if (scheduleService.addSchedule(schedule)) {
            return ResponseEntity.ok("添加成功");
        } else {
            return ResponseEntity.badRequest().body("添加失败");
        }
    }

    /**
     * 修改已有日程
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateSchedule(@RequestBody Schedule schedule) {
        if (scheduleService.updateSchedule(schedule)) {
            return ResponseEntity.ok("更新成功");
        } else {
            return ResponseEntity.badRequest().body("更新失败");
        }
    }

    /**
     * 删除日程
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id) {
        if (scheduleService.deleteSchedule(id)) {
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.badRequest().body("删除失败");
        }
    }
}
