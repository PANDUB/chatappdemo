package com.chatapp.component;

import java.util.Map;

public class CalculatorTool implements ToolHandler{

    @Override
    public String execute(Map<String, Object> input) {
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
    
