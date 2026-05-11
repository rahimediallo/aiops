package com.rahim.aiops.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiSreChatService {

    private final ChatClient chatClient;

    public AiSreChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String ask(String question) {
        try {
            return chatClient.prompt()
                    .system("""
                            You are a senior Kubernetes SRE assistant.
                            Answer clearly with:
                            - diagnosis
                            - probable cause
                            - commands to run
                            - recommended fix
                            """)
                    .user(question)
                    .call()
                    .content();

        } catch (Exception e) {
            return """
                    AI assistant unavailable.

                    Generic SRE guidance:
                    - Check pod logs: kubectl logs <pod-name> -n <namespace>
                    - Describe pod: kubectl describe pod <pod-name> -n <namespace>
                    - Check events: kubectl get events -n <namespace> --sort-by=.metadata.creationTimestamp
                    - Verify deployment, config maps, secrets and probes
                    """;
        }
    }
}