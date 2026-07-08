package com.chatapp.component;

import java.util.Map;

import org.springframework.stereotype.Component;

// ToolExecutor.java
@Component
public class ToolExecutor {

    public String execute(String toolName, Map<String, Object> input) {
        if ("calculator".equals(toolName)) {
            return runCalculator(input);
        }
        return "Error: unknown tool " + toolName;
    }

    private String runCalculator(Map<String, Object> input) {
        double a = ((Number) input.get("a")).doubleValue();
        double b = ((Number) input.get("b")).doubleValue();
        String operation = (String) input.get("operation");

        double result = switch (operation) {
            case "add" -> a + b;
            case "subtract" -> a - b;
            case "multiply" -> a * b;
            case "divide" -> {
                if (b == 0) throw new ArithmeticException("Division by zero");
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Unknown operation: " + operation);
        };

        return String.valueOf(result);
    }
}
