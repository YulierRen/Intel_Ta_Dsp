package com.lty.www.intel_ta_dsp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lty.www.intel_ta_dsp.aiservice.TongyiService;
import com.lty.www.intel_ta_dsp.dto.aidto.AiSchedule;
import com.lty.www.intel_ta_dsp.dto.aidto.ScheduleGenerateDTO;
import com.lty.www.intel_ta_dsp.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "schedule_generate_topic", consumerGroup = "schedule_generate_group")
public class ScheduleGenerateConsumer implements RocketMQListener<ScheduleGenerateDTO> {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TongyiService tongyiService;


    private final static ObjectMapper mapper = new ObjectMapper();


    /**
     * 构建 prompt，包含用户目标、时间范围和已占用时间段
     */
    public String buildPrompt(ScheduleGenerateDTO dto, String occupiedTimeInfo) {
        return """
你是一个日程安排 AI，任务是根据用户的学习或成长目标，在指定时间范围内生成合理的日程计划。你必须生成 **与目标高度相关的任务**，并确保返回格式是 Java 后端可直接解析的 **纯 JSON 数组**（无解释文字）。

### 用户目标：
%s

请你首先理解目标并将其拆解为多个可执行的小任务。

### 时间范围：
从 %s 到 %s
- 请按“天”进行任务分布（如时间为多天，则合理分散；如仅一天，则控制任务数量）。
- 任务时间必须完全落在此范围内。
- 建议安排在每日 08:00 至 22:00 之间，避免凌晨和深夜。

### 已占用时间段：
%s

请避开上述时间段，确保生成的任务时间段不与其冲突。任务之间也不得重叠。

### 输出格式要求：
请直接生成 JSON 数组，数组中的每一项为一个任务对象，结构如下：

```json
{
  "title": "不超过九字的任务标题",
  "description": "任务说明，必须贴合目标",
  "startTime": "yyyy-MM-dd HH:mm:ss",
  "endTime": "yyyy-MM-dd HH:mm:ss",
  "location": "任务地点，自主设定"
}
""".formatted(
                dto.getRequirements(),
                dto.getStartTime(),
                dto.getEndTime(),
                occupiedTimeInfo
        );
    }


    @Override
    public void onMessage(ScheduleGenerateDTO dto) {
        try {
            // 查询已存在的日程
            List<Schedule> existingSchedules = scheduleService.findFromStartToEnd(dto);
            StringBuilder occupiedTimes = new StringBuilder();
            if (!existingSchedules.isEmpty()) {
                occupiedTimes.append("以下时间段已被占用，请避免安排冲突：");
                for (Schedule s : existingSchedules) {
                    occupiedTimes.append(String.format("%s 至 %s；",
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                    .format(s.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                    .format(s.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    ));
                }
            }

            // 构造 prompt
            String prompt = buildPrompt(dto, occupiedTimes.toString());

            // 调用 AI
            String reScheduleJson = tongyiService.generate(prompt);

            // 解析 & 入库
            AiSchedule[] aiSchedules = parseSchedules(reScheduleJson);
            for (AiSchedule s : aiSchedules) {
                scheduleService.addSchedule(s, dto.getUserId());
            }

            System.out.println("✅ 日程生成并保存成功: " + dto.getUserId());

        } catch (Exception e) {
            e.printStackTrace();
        }
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
}