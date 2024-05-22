package com.github.yaowenbin.basic;

import com.github.yaowenbin.ApiKeys;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.qianfan.QianfanChatModel;

public class ChatLanguageModelExample {

    public static void main(String[] args) {
        ChatLanguageModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_API_KEY);
        model.generate("Say Hello World");

        ChatLanguageModel model2 = QianfanChatModel.builder()
                .apiKey(ApiKeys.QIANFAN_API_KEY)
                .secretKey(ApiKeys.QIANFAN_SECRET_KEY)
                .modelName("Yi-34B-Chat")
                .build();
        model2.generate("Say Hello World");
    }

}
