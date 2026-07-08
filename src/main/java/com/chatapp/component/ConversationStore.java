package com.chatapp.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import com.chatapp.dto.Message;


@Component
public class ConversationStore {

    private final Map<String, List<Message>> sessions = new ConcurrentHashMap<>();
    private final Map<String, List<Map<String, Object>>> rawSessions = new ConcurrentHashMap<>();

    public List<Message> getHistory(String sessionId) {
        return sessions.computeIfAbsent(sessionId, id -> new CopyOnWriteArrayList<>());
    }

    public void append(String sessionId, Message message) {
        getHistory(sessionId).add(message);
    }
    public List<Map<String, Object>> getRawHistory(String sessionId) {
        return rawSessions.computeIfAbsent(sessionId, id -> new ArrayList<>());
    }

    public void saveRawHistory(String sessionId, List<Map<String, Object>> messages) {
        rawSessions.put(sessionId, messages);
    }

    public void clear(String sessionId) {
        sessions.remove(sessionId);
    }
}
