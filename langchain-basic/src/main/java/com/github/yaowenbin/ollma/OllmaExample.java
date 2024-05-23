package com.github.yaowenbin.ollma;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.testcontainers.containers.GenericContainer;

public class OllmaExample {
    public static void main(String[] args) {
        // Build the ChatLanguageModel
        ChatLanguageModel model =
                OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3").build();

        // Example usage
        String answer = model.generate("Provide 3 short bullet points explaining why Java is awesome");
        System.out.println(answer);

        // Stop the Ollama container
    }

}
