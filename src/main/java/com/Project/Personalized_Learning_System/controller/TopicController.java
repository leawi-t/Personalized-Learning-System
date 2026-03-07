package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.topicDto.*;
import com.Project.Personalized_Learning_System.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<PagedModel<TopicResponseDto>> getTopics(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end,
            Pageable pageable
    ) {
        Page<TopicResponseDto> page = topicService.getTopics(pageable, subjectId, name, description, start, end);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicDetailDto> getTopicById(@PathVariable long topicId){
        return ResponseEntity.ok(topicService.getTopicById(topicId));
    }

    // the id is in the dto
    @PostMapping
    public ResponseEntity<TopicDetailDto> createTopic(@RequestBody @Valid TopicRequestDto dto){
        return new ResponseEntity<>(topicService.addTopic(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<TopicDetailDto> updateTopic(@RequestBody @Valid TopicUpdateDto dto,
                                                      @PathVariable long topicId){
        return new ResponseEntity<>(topicService.updateTopic(dto, topicId), HttpStatus.OK);
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> deleteTopicById(@PathVariable long topicId){
        topicService.deleteTopicById(topicId);
        return ResponseEntity.noContent().build();
    }
}
