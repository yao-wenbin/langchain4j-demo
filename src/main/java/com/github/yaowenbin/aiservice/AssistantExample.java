package com.github.yaowenbin.aiservice;

import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.service.AiServices;

public class AssistantExample {

    public static void main(String[] args) {
        var assistant = AiServices.create(Assistant.class, DefaultModels.qianfan());
        var res = assistant.chat("say hello world");
        System.out.println(res);
    }

    interface Assistant {
        String chat(String message);
    }

}
