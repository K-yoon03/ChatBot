package com.chatbot.chat.configuration;

import ch.qos.logback.classic.LoggerContext;
import com.chatbot.chat.service.ChatService;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class CliConfig {
    @ConditionalOnProperty(prefix = "spring.application", name="cli", havingValue = "true")
    @Bean
    public CommandLineRunner commandLineRunner(@Value("${spring.application.name}") String applicationName, ChatService chatService) {
        return args -> {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            context.getLogger("ROOT").detachAppender("CONSOLE");

            System.out.println("["+applicationName+"] CLI ChatBot Activated!");
            System.out.println("["+applicationName+"] CLI ChatBot Started!");
            System.out.println("["+applicationName+"] EXIT : input 'exit' or 'quit'...");

            try(Scanner scanner = new Scanner(System.in)) {
                while(true) {
                    System.out.print("\nUser: ");
                    String user = scanner.nextLine();

                    if(user.equalsIgnoreCase("exit") || user.equalsIgnoreCase("quit")) {
                        System.out.println("[" + applicationName + "]STOP APPLICATION.");
                        break;
                    }
                    System.out.print("ASSISSTANT: ");

                    Iterable<String> chatStream = chatService.stream(new Prompt(user), "cli").toIterable();

                    for(String token : chatStream) {
                        System.out.print(token);
                    }
                    System.out.println();
                }
            }
        };
    }
}
