package com.github.yaowenbin.aiservice;

import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

public class SystemMessageExample {

    public static void main(String[] args) {
        var friend = AiServices.create(AssistantExample.Assistant.class, DefaultModels.qianfan());
        var res = friend.chat("I am anxiety right");
        System.out.println(res);
    }

    interface Friend {
        @SystemMessage("you are a very good friend of mine, always give me some positive advice.")
        String chat(String message);
    }

}
