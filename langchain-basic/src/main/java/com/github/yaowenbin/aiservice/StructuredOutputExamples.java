package com.github.yaowenbin.aiservice;

import com.github.yaowenbin.ApiKeys;
import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;

import java.time.LocalDate;

public class StructuredOutputExamples {

    static class SentimentAnalyzeExample {

        enum Sentiment {
            POSITIVE, NEUTRAL, NEGATIVE
        }

        interface SentimentAnalyzer {
            @UserMessage("帮助我分析这段文本的情感: {{it}}")
            com.github.yaowenbin.aiservice.StructuredOutputSentimentExample.Sentiment analyzeSentiment(String text);

            @UserMessage("这段文本是否是积极的: {{it}}")
            boolean isPositive(String text);
        }

        public static void main(String[] args) {
            var sentimentAnalyzer = AiServices.create(com.github.yaowenbin.aiservice.StructuredOutputSentimentExample.SentimentAnalyzer.class, DefaultModels.openAi());
            var sentiment = sentimentAnalyzer.analyzeSentiment("这真是太棒了");
            System.out.println(sentiment);

            var isPositive = sentimentAnalyzer.isPositive("今天的天气可真好");
            System.out.println(isPositive);
        }
    }

    static class POJOExtractingExample {

        static class Person {

            private String firstName;
            private String lastName;
            private LocalDate birthDate;

            @Override
            public String toString() {
                return "Person {" +
                        " firstName = \"" + firstName + "\"" +
                        ", lastName = \"" + lastName + "\"" +
                        ", birthDate = " + birthDate +
                        " }";
            }
        }

        interface PersonExtractor {

            @UserMessage("Extract information about a person from {{it}}")
            Person extractPersonFrom(String text);
        }

        public static void main(String[] args) {

            ChatLanguageModel chatLanguageModel = OpenAiChatModel.builder()
                    .apiKey(ApiKeys.OPENAI_API_KEY)
                    .responseFormat("json_object")
                    .build();

            PersonExtractor extractor = AiServices.create(PersonExtractor.class, chatLanguageModel);

            String text = """
            1968年，在独立日逐渐消退的回声中，
            一个名叫约翰的孩子来到了平静的夜空下。
            这个新生儿，姓Doe，标志着新旅程的开始。
            """;

            Person person = extractor.extractPersonFrom(text);

            System.out.println(person); // Person { firstName = "约翰", lastName = "Doe", birthDate = 1968-07-04 }
        }
    }




}
