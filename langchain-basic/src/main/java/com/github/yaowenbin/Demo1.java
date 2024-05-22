package com.github.yaowenbin;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class Demo1 {
    public static void main(String[] args) {
        String apiKey = "demo";
        OpenAiChatModel model = OpenAiChatModel.withApiKey(apiKey);

        String answer = model.generate("Say 'Hello World'");
        System.out.println(answer); // Hello World

    }
}
