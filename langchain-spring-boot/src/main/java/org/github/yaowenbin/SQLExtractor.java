package org.github.yaowenbin;

import dev.langchain4j.service.SystemMessage;

public interface SQLExtractor {

    @SystemMessage("请你帮助我从这段语句查找包含的SQL语句: {{it}}，你只需要返回SQL语句的内容即可。如果{{it}}中没有可执行的SQL语句，则返回''")
    String extractSQL(String message);

}
