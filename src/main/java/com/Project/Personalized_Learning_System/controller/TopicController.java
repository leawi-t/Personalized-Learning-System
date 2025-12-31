package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.flashCardDto.CreateFlashCardDto;
import com.Project.Personalized_Learning_System.dto.flashCardDto.*;
import com.Project.Personalized_Learning_System.dto.noteDto.*;
import com.Project.Personalized_Learning_System.dto.questionDto.*;
import com.Project.Personalized_Learning_System.dto.topicDto.*;
import com.Project.Personalized_Learning_System.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    private final FlashCardService flashCardService;
    private final QuestionService questionService;
    private final NoteService noteService;

    @Autowired
    public TopicController(TopicService topicService, FlashCardService flashCardService,
                           QuestionService questionService, NoteService noteService){
        this.topicService = topicService;
        this.flashCardService = flashCardService;
        this.questionService = questionService;
        this.noteService = noteService;
    }

    @GetMapping()
    public ResponseEntity<List<TopicResponseDto>> getAllTopics(){
        return new ResponseEntity<>(topicService.getAllTopics(), HttpStatus.OK);
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicDetailDto> getTopicById(@PathVariable long topicId){
        return new ResponseEntity<>(topicService.getTopicById(topicId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TopicResponseDto>> searchTopic(@RequestParam String keyword){
        return new ResponseEntity<>(topicService.searchTopic(keyword), HttpStatus.OK);
    }

    @GetMapping("/{topicId}/flashcards")
    public ResponseEntity<Page<FlashCardDetailDto>> getFlashCardsByTopic(@PathVariable long topicId, Pageable pageable){
        return new ResponseEntity<>(flashCardService.getFlashCardByTopic(topicId, pageable), HttpStatus.OK);
    }

    @GetMapping("/{topicId}/questions")
    public ResponseEntity<Page<QuestionDetailsDto>> getQuestionsByTopic(@PathVariable long topicId, Pageable pageable){
        return new ResponseEntity<>(questionService.findByTopic(topicId, pageable), HttpStatus.OK);
    }

    @GetMapping("/{topicId}/notes")
    public ResponseEntity<Page<NoteResponseDto>> getNotesByTopic(@PathVariable long topicId, @RequestParam int pageNumber,
                                                                 @RequestParam int numberOfItems){
        Pageable pageable = PageRequest.of(pageNumber, numberOfItems);
        return new ResponseEntity<>(noteService.getNoteByTopic(topicId, pageable), HttpStatus.OK);
    }

    @PostMapping("/{topicId}/flashcards")
    public ResponseEntity<FlashCardDetailDto> createFlashCard(@RequestBody CreateFlashCardDto dto, @PathVariable long topicId){
        return new ResponseEntity<>(flashCardService.addFlashCard(dto, topicId), HttpStatus.CREATED);
    }

    @PostMapping("/{topicId}/questions")
    public ResponseEntity<QuestionDetailsDto> createQuestion(@RequestBody CreateQuestionDto dto, @PathVariable long topicId){
        return new ResponseEntity<>(questionService.addQuestion(dto, topicId), HttpStatus.CREATED);
    }

    @PostMapping(
            value = "/{topicId}/notes",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<NoteDetailDto> createNote(@PathVariable long topicId,  @RequestPart("dto") CreateNoteDto dto, @RequestPart("file") MultipartFile file) {
        System.out.println("Controller reached");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(noteService.addNote(dto, topicId, file));
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<TopicDetailDto> updateTopic(@RequestBody TopicUpdateDto dto, @PathVariable long topicId){
        return new ResponseEntity<>(topicService.updateTopic(dto, topicId), HttpStatus.OK);
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> deleteTopicById(@PathVariable long topicId){
        topicService.deleteTopicById(topicId);
        return ResponseEntity.noContent().build();
    }
}
