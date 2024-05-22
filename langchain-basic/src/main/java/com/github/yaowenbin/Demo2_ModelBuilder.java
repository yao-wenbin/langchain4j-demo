package com.github.yaowenbin;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import static com.github.yaowenbin.ApiKeys.OPENAI_API_KEY;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

/**
 * 使用ModelBuilder来自定义模型的参数，比如说OpenAI中的temperature温度、调用gpt-3 -turbo等
 */
public class Demo2_ModelBuilder {

    public static void main(String[] args) {
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(OPENAI_API_KEY)
                .modelName(GPT_3_5_TURBO)
                .temperature(0.5D) // 根据模型的不同参数值在,[0, 1] 越低精越精准，越高越有创意
                .build();

        String result = model.generate("请给我将一个关于Java的中文笑话");
        System.out.println(result);
    }
}
