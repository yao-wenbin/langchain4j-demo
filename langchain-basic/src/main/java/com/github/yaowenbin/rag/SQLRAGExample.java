package com.github.yaowenbin.rag;

import com.github.yaowenbin.Assistant;
import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

import static com.github.yaowenbin.rag.Utils.toPath;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;

public class SQLRAGExample {

    public static void main(String[] args) {
        String documentPath = "documents/";
        List<Document> documents = loadDocuments(toPath("documents/"), glob("*.sql"));

        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(DefaultModels.openAi())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();

        String result = assistant.chat("请帮助我生成执行搜索所有用户地址数量的SQL语句");
        System.out.println(result);

    }

    public static PathMatcher glob(String glob) {
        return FileSystems.getDefault().getPathMatcher("glob:" + glob);
    }
}
