package com.github.yaowenbin.aiservice;

import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;

public class MultiAiServiceExample {

    interface GreetingExpert {
        @UserMessage("分析这句话是否是一句简单的问候语: {{it}}")
        boolean isGreeting(String text);
    }

    interface ChatBot {
        @UserMessage("你是一个礼貌的聊天机器人，请帮助我解答这个问题: {{it}}")
        String chat(String text);
    }

    static class LLMService {
        final GreetingExpert greetingExpert;
        final ChatBot chatBot;
        final String GREETING_MESSAGE = "你好，我是你的聊天助手，请问有什么可以帮到你?";


        public LLMService(GreetingExpert greetingExpert, ChatBot chatBot) {
            this.greetingExpert = greetingExpert;
            this.chatBot = chatBot;
        }

        public String handle(String message) {
            if (greetingExpert.isGreeting(message)) {
                System.out.println("识别出该消息为问候语" + message);
                return GREETING_MESSAGE;
            }  else {
                return chatBot.chat(message);
            }
        }
    }

    public static void main(String[] args) {
        var greetingExport = AiServices.create(GreetingExpert.class, DefaultModels.openAi());
        var chatBot = AiServices.builder(ChatBot.class)
                .chatLanguageModel(DefaultModels.qianfan())
                .chatMemoryProvider(chatMemoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .build();

        var service = new LLMService(greetingExport, chatBot);

        var res = service.handle("你好"); //你好，我是你的聊天助手，请问有什么可以帮到你?
        System.out.println(res);

        res = service.handle("你好，请告诉我厦门本地有什么美食?"); // ....
        System.out.println(res);



    }

}
