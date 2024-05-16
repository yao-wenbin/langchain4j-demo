package com.github.yaowenbin.chatmemory;

import com.github.yaowenbin.Assistant;
import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.List;
import java.util.Map;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;

public class ChatMemoryPersistentExample {

    public static void main(String[] args) {
        var chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryStore(new MapDBChatMemoryStore())
                .build();

        var assistant = AiServices.builder(Assistant.class)
                .chatMemory(chatMemory)
                .chatLanguageModel(DefaultModels.openAi())
                .build();
        //
        // var result = assistant.chat("Hello, my name is Yao Wenbin");
        // System.out.println(result);

        // 运行一次上面两行的内容，然后注释上面两行内容，再取消注释下面两行内容，再运行一次下面两行的内容。
        var resultWitName = assistant.chat("Hello, What is my name?");
        System.out.println(resultWitName);

        // 可以看到ChatMemory被保存了下来，即使开始了一段新的对话，也能够LLM也存储着上一次的内容。
    }

    static class MapDBChatMemoryStore implements ChatMemoryStore {
        final DB db = DBMaker.fileDB("chat.db").transactionEnable().make();
        final Map<String, String> map = db.hashMap("message", Serializer.STRING, Serializer.STRING).createOrOpen();

        @Override
        public List<ChatMessage> getMessages(Object memoryId) {
            String json = map.get((String) memoryId);
            return messagesFromJson(json);
        }

        @Override
        public void updateMessages(Object o, List<ChatMessage> list) {
            var json = messagesToJson(list);
            map.put((String) o , json);
            db.commit();
        }

        @Override
        public void deleteMessages(Object o) {
            map.remove((String) o);
            db.commit();
        }
    }
}
