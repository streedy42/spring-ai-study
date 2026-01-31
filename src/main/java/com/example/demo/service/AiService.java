package com.example.demo.service;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AiService {
  // 필드. ChatModel 빈 생성
  @Autowired
  private ChatModel chatModel;

  // 메소드
  public String generateText(String question) {

    // 시스템 메시지 작성. LLM에게 지시할 내용을 SystemMessage로 생성
    SystemMessage systemMessage = SystemMessage.builder()
      .text("사용자 질문에 대해 한국어로 답변을 해야 합니다.")
      .build();

    // 사용자 메시지 생성. UserMessage 생성
    UserMessage userMessage = UserMessage.builder()
      .text(question)
      .build();

    // 런타임 대화 옵션 생성
    ChatOptions chatOptions = ChatOptions.builder()
      .model("gpt-4o-mini")
      .temperature(0.3)
      .maxTokens(500)
      .build();
    
    // LLM 으로 전송할 Prompt 생성
    Prompt prompt = Prompt.builder()
      .messages(systemMessage, userMessage)
      .chatOptions(chatOptions)
      .build();

    // LLM에게 요청하고 응답받기
    ChatResponse chatResponse = chatModel.call(prompt);
    AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
    String answer = assistantMessage.getText();

    return answer;

  }

}
