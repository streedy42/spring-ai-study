package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AiService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;





@RestController
@RequestMapping("/ai")
@Slf4j
public class AiController {
  // 필드. AiService 빈 주입받음
  @Autowired
  private AiService aiService;

  // // ch01
  // @PostMapping(
  //   value="/chat",
  //   consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
  //   produces = MediaType.TEXT_PLAIN_VALUE
  // )

  // ch02. 요청 매핑 메소드
  @PostMapping(
    value = "/chat-model",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.TEXT_PLAIN_VALUE
  )
  public String chatModel(@RequestParam("question") String question) {
    String answerText = aiService.generateText(question);
    return answerText;
  }

  // ch02. 비동기 Chat-model 요청 매핑 메소드
  @PostMapping(
    value = "/chat-model-stream",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.APPLICATION_NDJSON_VALUE
  )
  public Flux<String> chatModelStream(@RequestParam("question") String question) {
    Flux<String> answerStreamText = aiService.generateStreamText(question);
    return answerStreamText;
  }
  }
  



  // public String chat(@RequestParam("question") String question) {

      
  //     return "아직 모델과 연결되지 않았습니다.";
  // }
  
// }
