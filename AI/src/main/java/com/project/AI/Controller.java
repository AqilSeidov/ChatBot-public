package com.project.AI;

import HelperClasses.ChatRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/naai")
public class Controller {
   private final AIService aiService;
   public Controller(AIService aiService) {
       this.aiService = aiService;
   }
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody ChatRequest chatRequest) {
        String response = aiService.getResponse(chatRequest);
        return Map.of("response", response); // Return a JSON object
    }
}


