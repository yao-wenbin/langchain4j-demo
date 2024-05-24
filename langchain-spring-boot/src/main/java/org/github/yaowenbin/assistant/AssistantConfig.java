package org.github.yaowenbin.assistant;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.qianfan.QianfanChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.github.yaowenbin.SQLExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.sql.SQLException;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Configuration
@Slf4j
public class AssistantConfig {

    @Bean
    @Primary
    public ChatLanguageModel qianfanChatLanguageModel() {
        return QianfanChatModel.builder()
                .modelName("ERNIE-Speed-8K")
                .endpoint("ernie_speed")
                .apiKey(System.getenv("QIANFAN_API_KEY"))
                .secretKey(System.getenv("QIANFAN_SECRET_KEY"))
                .responseFormat("json_object")
                .build();
    }

    @Bean
    public ChatLanguageModel openAiChatLanguageModel() {
        return OpenAiChatModel.withApiKey("demo");
    }

    @Bean
    public Assistant assistant(ChatLanguageModel qianfanChatLanguageModel) {

        var builder = AiServices.builder(Assistant.class)
                .chatLanguageModel(qianfanChatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10));


        try {
            var file = ResourceUtils.getFile("classpath:schema.sql");
            var path = Paths.get(file.toURI());

            Document document = loadDocument(path);

            InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
            EmbeddingStoreIngestor.ingest(document, embeddingStore);
            builder.contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore));
        } catch (FileNotFoundException e) {
            log.error("schema.sql not found", e);
        }

        return builder.build();
    }
    @Bean
    public SQLExtractor sqlExtractor(ChatLanguageModel openAiChatLanguageModel) {

        var builder = AiServices.builder(SQLExtractor.class)
                .chatLanguageModel(openAiChatLanguageModel);

        return builder.build();
    }



}
