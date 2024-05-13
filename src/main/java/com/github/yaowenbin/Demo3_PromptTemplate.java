package com.github.yaowenbin;

import dev.langchain4j.model.input.PromptTemplate;

import java.util.HashMap;

public class Demo3_PromptTemplate {

    public static void main(String[] args) {
        var model = DefaultOpenAiModel.instance();

        var template = "创建一个关于 {{dishType}} 的烹饪配方，需要准备以下材料：{{ingredients}}。";
        PromptTemplate promptTemplate = PromptTemplate.from(template);

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("ingredients", "1. 面粉\n2. 盐\n3. 姜\n4. 料酒\n5. 葱\n6. 蒜\n7. 花椒\n8. 生抽\n9. 料酒\n10. 盐");
        variables.put("dishType", "意面");
        var prompt = promptTemplate.apply(variables);

        var response = model.generate(prompt.text());
        System.out.println(response);

    }
}
