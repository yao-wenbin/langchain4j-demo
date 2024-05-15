package com.github.yaowenbin.basic;

import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;

public class MultipleMessagesExample {

    public static void main(String[] args) {
        var model = DefaultModels.openAi();
        UserMessage firstMessage = UserMessage.from("Give me three advices about how to be a better programmer.");
        AiMessage firResponse = model.generate(firstMessage).content();
        System.out.println("First Response: \n" + firResponse.text());

        UserMessage secondMessage = UserMessage.from("more detail about first advice");

        var res = model.generate(firstMessage, firResponse, secondMessage);
        System.out.println("Second Response: \n" + res.content().text());
    }


}
