package com.chatapp.component;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

// ToolDefinitions.java
@Component
public class ToolDefinitions {

    public List<Map<String, Object>> getTools() {
        return List.of(
            Map.of(
                "name", "calculator",
                "description", "Performs basic arithmetic. Use this whenever a math calculation is needed instead of computing it yourself.",
                "input_schema", Map.of(
                    "type", "object",
                    "properties", Map.of(
                        "operation", Map.of(
                            "type", "string",
                            "enum", List.of("add", "subtract", "multiply", "divide")
                        ),
                        "a", Map.of("type", "number"),
                        "b", Map.of("type", "number")
                    ),
                    "required", List.of("operation", "a", "b")
                )
            ),
            Map.of(
              "name", "get_weather",
               "description", "Fetches current weather for a given city. Use when the user asks about weather or temperature.",
                "input_schema", 
                Map.of("type", "object",
                "properties", Map.of(
                        "city", Map.of("type", "string", "description", "City name, e.g. 'London'")
                    ),
                    "required", List.of("city")
                )
            ),
            Map.of(
                "name" , "search_knowledge_base",
                "description", "Searches internal documentation for technical facts (e.g. JVM internals, Spring config). Use when the user asks something that may be answered by internal docs rather than general knowledge.",
                "input_schema",Map.of(
                    "type", "object",
                    "properties", Map.of(
                        "query", Map.of("type", "string", "description", "Search query")
                    ),"required", List.of("query")
                ))

            

        );
    }
}