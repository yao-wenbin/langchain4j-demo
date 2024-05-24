package org.github.yaowenbin.assitant;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.github.yaowenbin.AIApplicationTest;
import org.github.yaowenbin.SQLExtractor;
import org.github.yaowenbin.assistant.Assistant;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

public class AssistantTest extends AIApplicationTest {

    @Resource
    Assistant assistant;

    @Resource
    SQLExtractor sqlExtractor;

    @Resource
    ChatLanguageModel chatLanguageModel;

    @Resource
    JdbcTemplate sqlQueryHandler;

    @Test
    public void configIsCorrectLoaded() {
        assertThat(assistant).isNotNull();
        assertThat(chatLanguageModel).isNotNull();
    }

    @Test
    public void generateSQL() {
        var response = assistant.generateSQL("请帮助我查询报价单表的数量");
        System.out.println(response);
    }

    @Test
    public void sqlExtract() {
        var res = sqlExtractor.extractSQL("""
                根据提供的表结构信息，我无法直接查询报价单表(`offer`)的数量，因为没有提供该表的字段信息。不过，我可以为您提供一个通用的SQL查询语句，用于查询任何表的数量。您可以根据这个语句，结合实际的报价单表字段信息进行查询。
                                
                通用的SQL查询语句如下：
                                
                                
                ```sql
                SELECT COUNT(*) FROM `offer`;
                ```
                
                这条SQL语句会返回`offer`表中的记录数量。
        """);
        System.out.println(res);
    }

    @Test
    public void sqlQuery() {
        var res = sqlQueryHandler.queryForObject("SELECT COUNT(*) FROM `offer`", Object.class);
        System.out.println(res);
    }


}
