package com.github.yaowenbin.rag;

import com.github.yaowenbin.Assistant;
import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.bge.small.en.v15.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import static com.github.yaowenbin.rag.Utils.toPath;

public class CustomRAGExample {
    /**
     * 在这个案例中，我们会探索如何使用非进阶的技巧来构建与LLM的简单交互
     */
    public static void main(String[] args) {
        String documentPath = "documents/miles-of-smiles-terms-of-use.txt";
        DocumentParser documentParser = new TextDocumentParser();

        Document document = FileSystemDocumentLoader.loadDocument(toPath(documentPath), documentParser);

        var splitter = DocumentSplitters.recursive(300, 0);
        var segments = splitter.split(document);

        //现在，我们需要嵌入（也称为“向量化”）这些片段。执行相似性搜索需要嵌入。
        //对于此示例，我们将使用本地进程内嵌入模型，但您可以选择任何支持的模型。
        //Langchain4j目前支持10多个流行的嵌入模型提供者。
        var embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();
        var embeddings = embeddingModel.embedAll(segments).content();

        //接下来，我们将这些嵌入存储在一个嵌入存储中（也称为“向量数据库”）。
        //此存储将用于在每次与LLM交互期间搜索相关段。
        //为简单起见，此示例使用内存中嵌入存储，但您可以从任何受支持的存储中进行选择。
        //Langchain4j目前支持超过15个流行的嵌入存储。
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        embeddingStore.addAll(embeddings, segments);

        var contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(2)
                .minScore(0.5)
                .build();

        var chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        var assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(DefaultModels.openAi())
                .chatMemory(chatMemory)
                .contentRetriever(contentRetriever)
                .build();


        assistant.chat("hello, Who are you?");
    }
}
