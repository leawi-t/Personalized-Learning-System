package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.subjectDto.*;
import com.Project.Personalized_Learning_System.dto.topicDto.*;
import com.Project.Personalized_Learning_System.service.SubjectService;
import com.Project.Personalized_Learning_System.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class SubjectController {

    private final SubjectService subjectService;
    private final TopicService topicService;

    @Autowired
    public SubjectController(SubjectService subjectService, TopicService topicService){
        this.subjectService = subjectService;
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> getAllSubjects(){
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<SubjectDetailDto> getCategoryById(
            @PathVariable long categoryId) {

        return ResponseEntity.ok(subjectService.getCategoryById(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SubjectResponseDto>> searchCategory(@RequestParam String keyword){
        return new ResponseEntity<>(subjectService.searchCategory(keyword), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/topics")
    public ResponseEntity<List<TopicResponseDto>> getTopicsByCategoryId (@PathVariable long categoryId){
        return new ResponseEntity<>(topicService.getTopicByCategory(categoryId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/categories")
    public ResponseEntity<SubjectDetailDto> createCategory(@RequestBody SubjectRequestDto dto, @PathVariable long userId){
        return new ResponseEntity<>(subjectService.addCategory(dto, userId), HttpStatus.CREATED);
    }

    @PostMapping("/{categoryId}/topics")
    public ResponseEntity<TopicDetailDto> createTopic(@RequestBody CreateTopicDto dto, @PathVariable long categoryId){
        return new ResponseEntity<>(topicService.addTopic(dto, categoryId), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<SubjectDetailDto> updateCategory(
            @RequestBody SubjectUpdateDto dto,
            @PathVariable long categoryId) {

        return ResponseEntity.ok(subjectService.updateCategory(dto, categoryId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteById(@PathVariable long categoryId){
        subjectService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}

