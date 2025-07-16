package com.lty.www.intel_ta_dsp.aidiarycontroller;

import com.lty.www.intel_ta_dsp.aiservice.DeepSeekService;
import com.lty.www.intel_ta_dsp.dto.aidto.AiDiaryDTO;
import com.lty.www.intel_ta_dsp.entity.Schedule;
import com.lty.www.intel_ta_dsp.entity.UserDaynote;
import com.lty.www.intel_ta_dsp.service.ScheduleService;
import com.lty.www.intel_ta_dsp.service.UserDaynoteService;
import com.lty.www.intel_ta_dsp.service.UserDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class AiDiaryController {

    private DeepSeekService deepSeekService;

    private ScheduleService scheduleService;

    private UserDaynoteService userDaynoteService;

    private UserDiaryService userDiaryService;

    public static String buildPrompt(List<Schedule> schedules, List<UserDaynote> notes) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("请用第一人称“你”来叙述最近几天的学习和生活安排，包括日程和每日总结。")
                .append("注意要有逻辑地串联每天的内容，整体语言要通顺流畅，有情绪表达和主观感受。\n\n");

        // 日程部分（按时间升序排列）
        schedules.sort(Comparator.comparing(Schedule::getStartTime));
        if (!schedules.isEmpty()) {
            prompt.append("以下是我的日程安排：\n");
            for (Schedule s : schedules) {
                prompt.append(String.format(
                        "- 在 %tF，你安排了“%s”（%s），地点：%s，状态：%s\n",
                        s.getStartTime(),
                        s.getTitle(),
                        s.getDescription(),
                        s.getLocation() == null ? "无" : s.getLocation(),
                        Boolean.TRUE.equals(s.getIsCompleted()) ? "已完成" : "未完成"
                ));
            }
            prompt.append("\n");
        }

        // 日记部分（按日期升序）
        notes.sort(Comparator.comparing(UserDaynote::getDate));
        if (!notes.isEmpty()) {
            prompt.append("以下是你每天的日记记录：\n");
            for (UserDaynote n : notes) {
                prompt.append(String.format(
                        "- %s 的日记标题为“%s”：%s\n",
                        n.getDate(),
                        n.getTitle(),
                        n.getDescription()
                ));
            }
            prompt.append("\n");
        }

        prompt.append("请根据以上信息，用一段自然的文字总结这些天你的经历和收获。");
        return prompt.toString();
    }

    @GetMapping("/generateDiary")
    public String ask(@RequestBody AiDiaryDTO) {

    }
}
