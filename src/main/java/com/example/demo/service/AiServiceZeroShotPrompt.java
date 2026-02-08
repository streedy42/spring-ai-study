package com.example.demo.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AiServiceZeroShotPrompt {
  
  // 필드
  private ChatClient chatClient;
  private PromptTemplate promptTemplate = PromptTemplate.builder()
    .template("""
        해당 내용을 요약해줘
        요약: {review}
        """)
    .build();
  
  public AiServiceZeroShotPrompt(ChatClient.Builder chatClientBuilder) {
    chatClient = chatClientBuilder
      .defaultOptions(ChatOptions.builder()
        .temperature(1.0)
        .maxTokens(100)
        .build())
      .build();    
  }

  // 메소드
  public String zeroShotPrompt(String review) {
    String sentiment = chatClient.prompt()
      .user(promptTemplate.render(Map.of("review", review)))
      .call()
      .content();
    return sentiment;
  }

}
