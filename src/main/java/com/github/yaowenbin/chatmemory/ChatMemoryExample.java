package com.github.yaowenbin.chatmemory;

import com.github.yaowenbin.Assistant;
import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;

public class ChatMemoryExample {



    public static void main(String[] args) {
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        var assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(DefaultModels.openAi())
                .chatMemory(chatMemory)
                .build();


        String res = assistant.chat("hello my name is yao wenbin");
        System.out.println(res); // Hello Yao Wenbin! How can I assist you today?

        String res2 = assistant.chat("what is my name ?");
        System.out.println(res2); // Your name is Yao Wenbin.
    }
}
