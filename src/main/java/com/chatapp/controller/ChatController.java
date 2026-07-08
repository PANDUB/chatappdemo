package com.chatapp.controller;

import com.chatapp.dto.ChatRequest;
import com.chatapp.dto.ChatResponse;
import com.chatapp.service.ClaudeService;
import com.chatapp.service.ConversationStore;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ClaudeService claudeService;
    private final ConversationStore conversationStore;

    public ChatController(ClaudeService claudeService,ConversationStore conversationStore) {
        this.claudeService = claudeService;
        this.conversationStore=conversationStore;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        String sessionId = (request.sessionId() != null)
                ? request.sessionId()
                : UUID.randomUUID().toString();
        String reply = claudeService.chat(sessionId,request.message());
        return ResponseEntity.ok(new ChatResponse(sessionId,reply));
    }
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> clearSession(@PathVariable String sessionId) {
        conversationStore.clear(sessionId);
        return ResponseEntity.noContent().build();
    }
}
