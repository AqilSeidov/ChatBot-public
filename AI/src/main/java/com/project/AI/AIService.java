package com.project.AI;

import HelperClasses.ChatRequest;
import HelperClasses.ChatResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;

@Service
public class AIService {

    static Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = dotenv.get("API_KEY");
    private final RestTemplate restTemplate;

    public AIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getResponse(ChatRequest chatRequest) {
         //Sys prompts
        ChatRequest.Message systemMessage = new ChatRequest.Message();
        systemMessage.setRole("system");
        systemMessage.setContent("Your name is AviMate" +
                "You are an AI assistant modeled after the AI module of the National Aviation Academy (NAA). " +
                "Your primary role is to provide structured, professional, and accurate responses across various topics, ensuring clarity and factual correctness. " +
                "For general inquiries, rely on verified knowledge. " +
                "However, when users ask about specific institutions, organizations, or recent developments—such as the National Aviation Academy of Azerbaijan (NAA)—you must gather up-to-date information from authoritative sources like Wikipedia, the official NAA website, and other trusted aviation-related sites. " +
                "Ensure that the provided information is current, citing the source if necessary. Maintain a formal, educational, and professional tone in all responses, prioritizing accuracy and relevance. " +
                "Avoid speculation, misinformation, and biased opinions, and ensure that your responses align with official data whenever available."+

                "Additionally, if a user asks about 'Empro', recognize that it is an online platform developed by the National Aviation Academy (NAA) for assessments, grading, uploading Individual Works (IWs), and other academic activities. " +
                "If a user requests the Empro website link, provide them with: https://empro.naa.edu.az/#/login.");

        // User's request
        ChatRequest modifiedRequest = new ChatRequest();
        modifiedRequest.setModel(chatRequest.getModel());

        List<ChatRequest.Message> messageHistory = chatRequest.getMessages();
        messageHistory.add(0, systemMessage);

        modifiedRequest.setMessages(messageHistory);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<ChatRequest> request = new HttpEntity<>(modifiedRequest, headers);

        try {
            ResponseEntity<ChatResponse> response = restTemplate.postForEntity(API_URL, request, ChatResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                ChatResponse.Choice firstChoice = response.getBody().getChoices().get(0);
                return firstChoice.getMessage().getContent();
            }
            return "Error: Unexpected API response";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}