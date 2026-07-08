package com.chatapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.chatapp.dto.Message;

@Service
public class ClaudeService {
    private final WebClient webClient;
     private final ConversationStore conversationStore;

    @Value("${claude.api.key}")
    private String apiKey;

    @Value("${claude.api.model}")
    private String model;

    @Value("${claude.api.version}")
    private String apiVersion;
    
    @Value("${claude.api.default-system-prompt}")
    private String defaultSystemPrompt;
   

    public ClaudeService(WebClient claudeWebClient,ConversationStore conversationStore) {
        this.webClient = claudeWebClient;
        this.conversationStore=conversationStore;
    }
    public String chat(String sessionId, String userMessage) {
        conversationStore.append(sessionId, new Message("user", userMessage));
              List<Message> history= conversationStore.getHistory(sessionId);


        Map<String, Object> body = Map.of(
                "model", model,
                "max_tokens", 1024,
                "messages", history.stream().
                        map( m ->  Map.of("role", m.role(), "content", 
                        m.content())).toList()
                );
        
        Map<String, Object> response = webClient.post()
                .header("x-api-key", apiKey)
                .header("anthropic-version", apiVersion)
                .header("content-type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block(); // block() is fine for a simple demo; use reactive chain in prod

        // response.content is a list of blocks; take the first text block

         List<Map<String, Object>> content = (List<Map<String, Object>>) response.get("content");
        String reply = (String) content.get(0).get("text");
        conversationStore.append(sessionId, new Message("assistant", reply));

        return reply;

}
}
