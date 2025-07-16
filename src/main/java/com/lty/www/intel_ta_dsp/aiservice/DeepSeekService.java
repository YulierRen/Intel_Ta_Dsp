package com.lty.www.intel_ta_dsp.aiservice;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DeepSeekService {

    public String ask(String prompt) {
        try {
            Generation gen = new Generation();
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(prompt)
                    .build();

            GenerationParam param = GenerationParam.builder()
                    // 推荐用环境变量读取
                    .apiKey("sk-813e807674cf45dea4d4111afa007063")
                    .model("deepseek-r1")
                    .messages(Collections.singletonList(userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();

            GenerationResult result = gen.call(param);

            return result.getOutput().getChoices().get(0).getMessage().getContent();

        } catch (NoApiKeyException e) {
            return "❌ 未设置 API Key，请设置环境变量 DASHSCOPE_API_KEY";
        } catch (InputRequiredException e) {
            return "❌ 请求参数缺失：" + e.getMessage();
        } catch (ApiException e) {
            return "❌ 调用 DashScope 接口失败：" + e.getMessage();
        } catch (Exception e) {
            return "❌ 未知错误：" + e.getMessage();
        }
    }
}
