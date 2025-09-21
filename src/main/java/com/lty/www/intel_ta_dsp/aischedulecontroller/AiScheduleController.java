package com.lty.www.intel_ta_dsp.aischedulecontroller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lty.www.intel_ta_dsp.aiservice.TongyiService;
import com.lty.www.intel_ta_dsp.dto.aidto.ScheduleGenerateDTO;
import com.lty.www.intel_ta_dsp.dto.aidto.AiSchedule;
import com.lty.www.intel_ta_dsp.entity.Schedule;
import com.lty.www.intel_ta_dsp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiScheduleController {
    private final ScheduleService scheduleService;
    private final TongyiService tongyiService;
    private final RocketMQTemplate rocketMQTemplate;



    @PostMapping("/generateSchedule")
    public ResponseEntity<?> generateSchedule(@RequestBody ScheduleGenerateDTO dto) {
        // 发送到 MQ（异步任务）
        rocketMQTemplate.convertAndSend("schedule_generate_topic", dto);

        // 立即返回
        return ResponseEntity.ok("任务已提交，生成日程中...");
    }
}
