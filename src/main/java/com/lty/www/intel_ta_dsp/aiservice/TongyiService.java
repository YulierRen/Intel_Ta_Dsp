package com.lty.www.intel_ta_dsp.aiservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class TongyiService {
    private static final String API_KEY = "sk-813e807674cf45dea4d4111afa007063";
    private static final String API_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

    public String generate(String prompt) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS) // 通义响应慢时要足够长
                .build();


        ObjectMapper mapper = new ObjectMapper();

        ObjectNode root = mapper.createObjectNode();
        root.put("model", "qwen-turbo");

// 构建 messages 数组
        ArrayNode messages = mapper.createArrayNode();

        messages.addObject()
                .put("role", "system")
                .put("content", "你是一个JSON格式响应的AI助手");

        messages.addObject()
                .put("role", "user")
                .put("content", prompt);  // prompt 是 buildPrompt(dto) 得到的单行字符串

        ObjectNode input = mapper.createObjectNode();
        input.set("messages", messages);

        root.set("input", input);

// parameters
        ObjectNode parameters = mapper.createObjectNode();
        parameters.put("result_format", "message");
        root.set("parameters", parameters);

// 转成 JSON 字符串
        String json = mapper.writeValueAsString(root);


        System.out.println(json);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", "Bearer " + API_KEY)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
