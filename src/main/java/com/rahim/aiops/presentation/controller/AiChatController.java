package com.rahim.aiops.presentation.controller;

import com.rahim.aiops.application.dto.ChatRequest;
import com.rahim.aiops.application.service.AiSreChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "AI SRE Assistant", description = "AI-powered Kubernetes SRE assistant")
public class AiChatController {

    private final AiSreChatService aiSreChatService;

    @PostMapping
    @Operation(
            summary = "Ask the AI SRE assistant",
            description = "Ask Kubernetes/SRE related questions and receive AI-generated troubleshooting guidance",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "question": "Why is payment-service in CrashLoopBackOff in namespace prod?"
                                            }
                                            """
                            )
                    )
            )
    )
    public String chat(@Valid @RequestBody ChatRequest request) {
        return aiSreChatService.ask(request.question());
    }
}