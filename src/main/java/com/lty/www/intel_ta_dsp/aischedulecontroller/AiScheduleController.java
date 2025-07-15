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

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 构建 prompt，包含用户目标、时间范围和已占用时间段
     */
    public String buildPrompt(ScheduleGenerateDTO dto, String occupiedTimeInfo) {
        return """
        你是一个日程生成 AI，根据用户提供的目标和时间范围，生成一份 Java 后端可用的 JSON 数组，表示该时间段内合理安排的与目标完全相关的日程任务。

        目标：%s
        时间范围：从 %s 到 %s
        %s

        请生成一个 JSON 数组，数组中的每一项代表一个任务，包含以下字段：

        - title：任务标题，必须紧密相关于目标（不超过九个字）
        - description：任务简要描述，必须与目标内容一致
        - startTime：任务开始时间，格式为 "yyyy-MM-dd HH:mm:ss"
        - endTime：任务结束时间，格式为 "yyyy-MM-dd HH:mm:ss"
        - location：任务地点，根据目标灵活生成

        要求：

        1. 只安排与目标相关的任务，不允许安排其他不相关内容。
        2. 任务时间必须在指定时间范围内。
        3. 一天内可以安排零到三条任务不等，且时间不重叠。
        4. 每个任务时长建议30分钟至3小时。
        5. 在整个时间范围内，合理安排任务在一天中的时间段。
        6. 返回纯 JSON 数组，无额外文字说明。
        """.formatted(dto.getRequirements(), dto.getStartTime(), dto.getEndTime(), occupiedTimeInfo);
    }

    /**
     * 解析 AI 返回 JSON 字符串中的 JSON 数组部分为 AiSchedule 数组
     */
    public static AiSchedule[] parseSchedules(String responseJson) throws IOException {
        JsonNode root = mapper.readTree(responseJson);
        String content = root.path("output")
                .path("choices").get(0)
                .path("message")
                .path("content")
                .asText();

        return mapper.readValue(content, AiSchedule[].class);
    }

    @PostMapping("/generateSchedule")
    public ResponseEntity<?> generateSchedule(@RequestBody ScheduleGenerateDTO dto) throws IOException {
        System.out.println("请求参数: " + dto);

        // 查询已存在的日程，生成占用时间段说明
        List<Schedule> existingSchedules = scheduleService.findFromStartToEnd(dto);
        StringBuilder occupiedTimes = new StringBuilder();
        if (!existingSchedules.isEmpty()) {
            occupiedTimes.append("以下时间段已被占用，请避免安排冲突：");
            for (Schedule s : existingSchedules) {
                occupiedTimes.append(String.format("%s 至 %s；",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(s.getStartTime().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDateTime()),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(s.getEndTime().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDateTime())
                ));
            }
        }

        // 构造 prompt
        String prompt = buildPrompt(dto, occupiedTimes.toString());

        // 调用 AI 生成日程 JSON 字符串
        String reScheduleJson = tongyiService.generate(prompt);

        // 解析 AI 返回的日程
        AiSchedule[] aiSchedules = parseSchedules(reScheduleJson);

        // 保存所有生成的日程到数据库
        for (AiSchedule s : aiSchedules) {
            scheduleService.addSchedule(s, dto.getUserId());
        }

        return ResponseEntity.ok().build();
    }
}
