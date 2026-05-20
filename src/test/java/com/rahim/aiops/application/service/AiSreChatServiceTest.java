package com.rahim.aiops.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.ai.chat.client.ChatClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AiSreChatServiceTest {

    @Test
    void ask_returnsAiResponse_whenChatClientResponds() {
        ChatClient chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);

        // stub deep chained calls: prompt().system(...).user(...).call().content()
        when(chatClient.prompt().system(anyString()).user(anyString()).call().content())
                .thenReturn("AI answer");

        // supply a builder mock that returns our chatClient when build() is called
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        when(builder.build()).thenReturn(chatClient);

        AiSreChatService service = new AiSreChatService(builder);

        String res = service.ask("hello");

        assertThat(res).isEqualTo("AI answer");
        // prompt() may be invoked during stubbing and during execution; ensure it was called at least once
        verify(chatClient, atLeastOnce()).prompt();
    }

    @Test
    void ask_returnsFallback_whenChatClientThrows() {
        ChatClient chatClient = mock(ChatClient.class);
        // make prompt() throw to trigger fallback
        when(chatClient.prompt()).thenThrow(new RuntimeException("down"));

        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        when(builder.build()).thenReturn(chatClient);
        AiSreChatService service = new AiSreChatService(builder);

        String res = service.ask("question");

        assertThat(res).contains("AI assistant unavailable");
        assertThat(res).contains("kubectl logs");
    }
}
