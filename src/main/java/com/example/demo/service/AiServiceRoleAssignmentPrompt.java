package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class AiServiceRoleAssignmentPrompt {
  // 필드
  private ChatClient chatClient;

  // 생성자
  public AiServiceRoleAssignmentPrompt(ChatClient.Builder chatClientBuilder) {
    chatClient = chatClientBuilder.build();
  }

  // 시스템 메시지를 활용해서 역할 부여하기
  public Flux<String> roleAssignment(String requirements) {
    Flux<String> artCurator = chatClient.prompt()
      // 시스템 메시지 추가
      .system("""
          당신은 미술관 큐레이터 입니다.
          아래 위치를 알려주면, 근처에 있는 유명한 미술관을 제안해주고, 이유를 달아주세요.
          경우에 따라 방문하고 싶은 장소를 제공할 수도 있습니다.
          출력 형식은 <ul> 태그이고, 미술관 장소는 굵게 표시해 주세요.
          """)
      // 사용자 메시지 추가
      .user("위치: %s".formatted(requirements))
      // 대화 옵션 설정
      .options(ChatOptions.builder()
        .temperature(1.0)
        .maxTokens(300)
        .build())
      .stream()
      .content();

    return artCurator;
  }

}
