package com.github.yaowenbin.basic;

import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.output.Response;

public class ResponseExample {

    public static void main(String[] args) {
        var model = DefaultModels.openAi();
        Response<AiMessage> response = model.generate(UserMessage.from("Say Hello World!"));

        System.out.println(response.content()); // AiMessage { text = "Hello World!" toolExecutionRequests = null }
        System.out.println(response.tokenUsage()); // TokenUsage { inputTokenCount = 11, outputTokenCount = 3, totalTokenCount = 14 }
        System.out.println(response.finishReason()); //STOP
    }
}
