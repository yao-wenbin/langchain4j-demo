package com.github.yaowenbin.functioncalling;

import com.github.yaowenbin.DefaultModels;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.output.Response;

import java.util.Collections;

public class GetWeatherExample {

    public static void main(String[] args) {
        // ToolSpecification toolSpecification = ToolSpecification.builder()
        //         .name("getWeather")
        //         .description("Returns the weather forecast for a given city")
        //         .addParameter("city", type("string"), description("The city for which the weather forecast should be returned"))
        //         .addParameter("temperatureUnit", enums(TemperatureUnit.class)) // enum TemperatureUnit { CELSIUS, FAHRENHEIT }
        //         .build();

        var toolSpecifications = ToolSpecifications.toolSpecificationsFrom(WeatherForecast.class);
        toolSpecifications.addAll(ToolSpecifications.toolSpecificationsFrom(Calculator2.class));

        var model = DefaultModels.openAi();

        UserMessage userMessage = UserMessage.from("What is the square root of 475695037565?");
        Response<AiMessage> response = model.generate(Collections.singletonList(userMessage), toolSpecifications);
        AiMessage aiMessage = response.content();

        System.out.println(aiMessage.toolExecutionRequests());
        System.out.println(aiMessage.text());
    }
}

class Calculator2 {

    @Tool("Square Root of a number")
    public double squareRoot(@P("the number of which the square root should be calculated")double x) {
        return Math.sqrt(x);
    }
}


class WeatherForecast {

    @Tool("Returns the weather forecast for a given city")
    String getWeather(
            @P("The city for which the weather forecast should be returned") String city,
            TemperatureUnit temperatureUnit
    ) {
        if (city.equalsIgnoreCase("London")) {
            return "It will be storm";
        }
        return "It's sunny";
    }
}

enum TemperatureUnit {
    CELSIUS, FAHRENHEIT
}

