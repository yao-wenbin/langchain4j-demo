package com.github.yaowenbin;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.qianfan.QianfanChatModel;

import java.time.Duration;

import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

public class DefaultModels {

    private static ChatLanguageModel openAIModel = OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .modelName(GPT_3_5_TURBO)
            .timeout(Duration.ofSeconds(60))
            .build();

    private static ChatLanguageModel qianfanModel = QianfanChatModel.builder()
            .modelName("Yi-34B-Chat")
            .apiKey(System.getenv("QIANFAN_API_KEY"))
            .secretKey(System.getenv("QIANFAN_SECRET_KEY"))
            .build();

    private DefaultModels() {
    }

    public static ChatLanguageModel openAi() {
        return openAIModel;
    }

    public static ChatLanguageModel qianfan() {
        return qianfanModel;
    }

}
