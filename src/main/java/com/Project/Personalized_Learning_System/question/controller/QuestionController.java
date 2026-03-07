package com.Project.Personalized_Learning_System.question.controller;

import com.Project.Personalized_Learning_System.question.dto.questionDto.FilterQuestionDto;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionRequestDto;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionResponseDto;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionUpdateDto;
import com.Project.Personalized_Learning_System.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService service;

    @GetMapping
    public ResponseEntity<PagedModel<QuestionResponseDto>> getQuestions(
        @Valid FilterQuestionDto dto,
        Pageable pageable
    ){
        Page<QuestionResponseDto> page = service.getQuestions(dto, pageable);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable long questionId){
        return ResponseEntity.ok(service.getQuestionById(questionId));
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(@RequestBody @Valid QuestionRequestDto dto){
        return new ResponseEntity<>(service.addQuestion(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@RequestBody QuestionUpdateDto dto, @PathVariable long questionId){
        return new ResponseEntity<>(service.updateQuestion(dto, questionId), HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable long questionId){
        service.deleteQuestionById(questionId);
        return ResponseEntity.noContent().build();
    }
}
