package org.github.yaowenbin.assistant;

import dev.langchain4j.service.SystemMessage;

public interface Assistant {

    @SystemMessage("""
        你是一个SQL助手，基于我为你提供的数据表结构信息，请输出一个包含sql字段的结构化json数据。
        在表结构中有着以下的中文映射关系：
        报价单: offer
        订单: order
        商品: product
        客户: customer
    """)
    String generateSQL(String message);

}
