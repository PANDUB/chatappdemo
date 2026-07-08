package com.chatapp.dto;

public record ChatRequest(String sessionId,String message,String systemPrompt) {
}
