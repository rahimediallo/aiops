package com.rahim.aiops.application.service;

import com.rahim.aiops.domain.model.Incident;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiIncidentAnalysisService {

    private final ChatClient chatClient;

    public AiIncidentAnalysisService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String analyzeIncident(Incident incident) {

        try {

            return chatClient.prompt()
                    .system("""
                            You are a senior Kubernetes SRE.
                            Analyze the incident and return:
                            - summary
                            - probable cause
                            - recommended actions
                            """)
                    .user("""
                            Namespace: %s
                            Pod: %s
                            Severity: %s
                            Status: %s
                            Message: %s
                            """.formatted(
                            incident.namespace(),
                            incident.podName(),
                            incident.severity(),
                            incident.status(),
                            incident.message()
                    ))
                    .call()
                    .content();

        } catch (Exception e) {

            return """
                    AI analysis unavailable.

                    Summary:
                    Pod %s in namespace %s reported issue:
                    %s

                    Probable cause:
                    CrashLoopBackOff after deployment may indicate:
                    - invalid configuration
                    - missing environment variables
                    - failing health checks
                    - application startup failure

                    Recommended actions:
                    - kubectl logs %s -n %s
                    - kubectl describe pod %s -n %s
                    - verify deployment changes
                    - check secrets and config maps
                    """.formatted(
                    incident.podName(),
                    incident.namespace(),
                    incident.message(),
                    incident.podName(),
                    incident.namespace(),
                    incident.podName(),
                    incident.namespace()
            );
        }
    }
}