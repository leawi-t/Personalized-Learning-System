package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.choiceDto.*;
import com.Project.Personalized_Learning_System.dto.questionDto.*;
import com.Project.Personalized_Learning_System.model.QuestionType;
import com.Project.Personalized_Learning_System.service.ChoiceService;
import com.Project.Personalized_Learning_System.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final ChoiceService choiceService;

    @Autowired
    public QuestionController(QuestionService questionService, ChoiceService choiceService){
        this.questionService = questionService;
        this.choiceService = choiceService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDetailsDto>> getAllQuestions(){
        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("/{questionId}/choices")
    public ResponseEntity<List<ChoiceDetailDto>> getAllChoices(@PathVariable long questionId){
        return new ResponseEntity<>(choiceService.getChoiceByQuestion(questionId), HttpStatus.OK);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDetailsDto> getQuestionById(@PathVariable long questionId){
        return new ResponseEntity<>(questionService.getQuestionById(questionId), HttpStatus.OK);
    }

    @GetMapping("/filter/byType")
    public ResponseEntity<List<QuestionDetailsDto>> getQuestionByType(@RequestParam QuestionType type){
        return new ResponseEntity<>(questionService.findByType(type), HttpStatus.OK);
    }

    @GetMapping("/filter/byDifficulty")
    public ResponseEntity<List<QuestionDetailsDto>> getQuestionByDifficulty(@RequestParam(defaultValue = "0") int lDifficulty ,
                                                                      @RequestParam(defaultValue = "5") int hDifficulty){
        return new ResponseEntity<>(questionService.filterByDifficulty(lDifficulty, hDifficulty), HttpStatus.OK);
    }

    @PostMapping("/{questionId}/choices")
    public ResponseEntity<ChoiceDetailDto> createChoice(@RequestBody CreateChoiceDto dto, @PathVariable long questionId){
        return new ResponseEntity<>(choiceService.addChoice(dto, questionId), HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDetailsDto> updateQuestion(@RequestBody QuestionUpdateDto dto, @PathVariable long questionId){
        return new ResponseEntity<>(questionService.updateQuestion(dto, questionId), HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable long questionId){
        questionService.deleteQuestionById(questionId);
        return ResponseEntity.noContent().build();
    }
}
