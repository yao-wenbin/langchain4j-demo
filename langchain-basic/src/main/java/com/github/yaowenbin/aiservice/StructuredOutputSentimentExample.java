package com.github.yaowenbin.aiservice;

import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;

public class StructuredOutputSentimentExample {

    enum Sentiment {
        POSITIVE, NEUTRAL, NEGATIVE
    }

    interface SentimentAnalyzer {
        @UserMessage("帮助我分析这段文本的情感: {{it}}")
        Sentiment analyzeSentiment(String text);

        @UserMessage("这段文本是否是积极的: {{it}}")
        boolean isPositive(String text);
    }

    public static void main(String[] args) {
        var sentimentAnalyzer = AiServices.create(SentimentAnalyzer.class, DefaultModels.openAi());
        var sentiment = sentimentAnalyzer.analyzeSentiment("这真是太棒了");
        System.out.println(sentiment);

        var isPositive = sentimentAnalyzer.isPositive("今天的天气可真好");
        System.out.println(isPositive);
    }
}
