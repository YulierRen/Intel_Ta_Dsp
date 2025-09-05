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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class AiDiaryController {

    @Autowired
    private DeepSeekService deepSeekService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserDaynoteService userDaynoteService;

    @Autowired
    private UserDiaryService userDiaryService;

    public static String buildPrompt(List<Schedule> schedules, List<UserDaynote> notes) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("请根据我的日程安排，完成情况和日记记录叙述我的经历与收获")
                .append("整体语言要通顺流畅，不同的经历要有不同的情绪色彩（如学习自律就要重经历和收获，出行旅游就要重情绪与优美文风），直叙时间经历与收获，最后来个总结。\n\n");

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

    @GetMapping("/generateDiaryStream")
    public SseEmitter generateDiaryStream(@RequestParam Long userId,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate) {
        AiDiaryDTO dto = new AiDiaryDTO();
        dto.setUserId(userId);
        dto.setStartTime(startdate);
        dto.setEndTime(enddate);

        SseEmitter emitter = new SseEmitter(0L); // 超时时间设置为无限

        new Thread(() -> {
            try {
                List<UserDaynote> userDaynotes = userDaynoteService.findFromStartToEnd(dto);
                List<Schedule> schedules = scheduleService.findFromStartToEnd(dto);
                String prompt = buildPrompt(schedules, userDaynotes);

                deepSeekService.streamAsk(
                        prompt,
                        reasoning -> {
                            try {
                                emitter.send(reasoning);
                            } catch (Exception e) {
                                emitter.completeWithError(e);
                            }
                        },
                        content -> {
                            try {
                                emitter.send(SseEmitter.event()
                                        .name("content")
                                        .data(content));
                            } catch (Exception e) {
                                emitter.completeWithError(e);
                            }
                        });

                emitter.complete();
                System.out.println("结束");
            } catch (Exception e) {
                System.out.println("异常");
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }

}
