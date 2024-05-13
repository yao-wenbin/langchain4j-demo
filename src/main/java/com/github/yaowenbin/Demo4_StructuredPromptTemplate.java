package com.github.yaowenbin;

import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;

import java.util.ArrayList;
import java.util.List;

public class Demo4_StructuredPromptTemplate {

    @StructuredPrompt({
            "创建一个只能够包含{{ingredients}}材料的{{dishType}}烹饪配方",
            "你的回答要遵循如下的结构:",

            "配方名称: ...",
            "描述: ...",
            "预计时间: ...",

            "所需材料:",
            "- ...",
            "- ...",

            "步骤:",
            "- ...",
            "- ...",
    })
    static class CreateRecipePrompt {

        String dishType;
        List<String> ingredients;

        public CreateRecipePrompt(String dishType, List<String> ingredients) {
            this.dishType = dishType;
            this.ingredients = ingredients;
        }
    }

    public static void main(String[] args) {
        var model = DefaultOpenAiModel.instance();

        var structuredPrompt = new CreateRecipePrompt("沙拉", List.of("黄瓜", "洋葱", "橄榄", "番茄"));
        Prompt prompt = StructuredPromptProcessor.toPrompt(structuredPrompt);
        var res = model.generate(prompt.text());

        System.out.println(res);

    }

}
