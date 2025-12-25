package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.categoryDto.*;
import com.Project.Personalized_Learning_System.dto.topicDto.*;
import com.Project.Personalized_Learning_System.service.CategoryService;
import com.Project.Personalized_Learning_System.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final TopicService topicService;

    @Autowired
    public CategoryController(CategoryService categoryService, TopicService topicService){
        this.categoryService = categoryService;
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailDto> getCategoryById(
            @PathVariable long categoryId) {

        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponseDto>> searchCategory(@RequestParam String keyword){
        return new ResponseEntity<>(categoryService.searchCategory(keyword), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/topics")
    public ResponseEntity<List<TopicResponseDto>> getTopicsByCategoryId (@PathVariable long categoryId){
        return new ResponseEntity<>(topicService.getTopicByCategory(categoryId), HttpStatus.OK);
    }

    @PostMapping("/{categoryId}/topics")
    public ResponseEntity<TopicDetailDto> createTopic(@RequestBody CreateTopicDto dto, @PathVariable long categoryId){
        return new ResponseEntity<>(topicService.addTopic(dto, categoryId), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailDto> updateCategory(
            @RequestBody CategoryUpdateDto dto,
            @PathVariable long categoryId) {

        return ResponseEntity.ok(categoryService.updateCategory(dto, categoryId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteById(@PathVariable long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}

