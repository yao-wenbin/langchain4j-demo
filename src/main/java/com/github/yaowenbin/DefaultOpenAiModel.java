package com.github.yaowenbin;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.time.Duration;

public class DefaultOpenAiModel {

    private static ChatLanguageModel model = OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .timeout(Duration.ofSeconds(60))
            .build();

    private DefaultOpenAiModel() {
    }

    public static ChatLanguageModel instance() {
        return model;
    }

}
