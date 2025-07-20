package com.lty.www.intel_ta_dsp.aiservice;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.lty.www.intel_ta_dsp.config.ApiConfig;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.function.Consumer;


@Service
public class DeepSeekService {

    @Autowired
    ApiConfig  apiConfig;

    private static final Logger logger = LoggerFactory.getLogger(DeepSeekService.class);

    /**
     * 构建 DashScope 的请求参数
     */
    private GenerationParam buildGenerationParam(Message userMsg) {
        return GenerationParam.builder()
                // 你也可以改为 System.getenv("DASHSCOPE_API_KEY")
                .apiKey(apiConfig.getApiKey())
                .model("deepseek-r1")
                .messages(Arrays.asList(userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .incrementalOutput(true)
                .build();
    }

    /**
     * 流式调用 DashScope，并通过回调方式处理输出
     *
     * @param prompt 用户输入的问题
     * @param onReasoning 消息中的思考过程回调（可为 null）
     * @param onContent    消息中的最终回复回调
     */
    public void streamAsk(String prompt, Consumer<String> onReasoning, Consumer<String> onContent) {
        try {
            Generation gen = new Generation();
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(prompt)
                    .build();

            GenerationParam param = buildGenerationParam(userMsg);
            Flowable<GenerationResult> result = gen.streamCall(param);

            result.blockingForEach(message -> {
                String reasoning = message.getOutput().getChoices().get(0).getMessage().getReasoningContent();
                String content = message.getOutput().getChoices().get(0).getMessage().getContent();

                if (reasoning != null && !reasoning.isEmpty() && onReasoning != null) {
                    onReasoning.accept(reasoning);
                }

                if (content != null && !content.isEmpty()) {
                    onContent.accept(content);
                }
            });

        } catch (NoApiKeyException e) {
            logger.error("未设置 API Key，请设置环境变量 DASHSCOPE_API_KEY");
        } catch (InputRequiredException e) {
            logger.error("请求参数缺失: {}", e.getMessage());
        } catch (ApiException e) {
            logger.error("调用 DashScope 接口失败: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("未知异常: {}", e.getMessage());
        }
    }
}
