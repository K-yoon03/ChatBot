package com.chatbot.chat.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatCleintBuilder) {
        this.chatClient = chatCleintBuilder.build();
    }

    @GetMapping("/ai")
    public String generate(String userPrompt){
        return this.chatClient.prompt()
                .user(userPrompt)
                .call()
                .content();
    }
}
