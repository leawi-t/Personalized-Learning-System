package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.flashCardDto.FlashCardDetailDto;
import com.Project.Personalized_Learning_System.dto.flashCardDto.FlashCardRequestDto;
import com.Project.Personalized_Learning_System.dto.flashCardDto.FlashCardResponseDto;
import com.Project.Personalized_Learning_System.dto.flashCardDto.FlashCardUpdateDto;
import com.Project.Personalized_Learning_System.service.FlashCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

//TODO: make the filter for the tag a list of tags instead of one tag

@RestController
@RequiredArgsConstructor
@RequestMapping("/flashcards")
public class FlashCardController {

    private final FlashCardService service;

    @GetMapping
    public ResponseEntity<PagedModel<FlashCardResponseDto>> getFlashCards(
            @RequestParam(required = false) Long topicId,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end,
            Pageable pageable
    ){
        Page<FlashCardResponseDto> page = service.getFlashCards(topicId, tag, min, max, start, end, pageable);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/{flashcardId}")
    public ResponseEntity<FlashCardDetailDto> getFlashCardById(@PathVariable long flashcardId){
        return ResponseEntity.ok(service.getFlashCardById(flashcardId));
    }

    @PostMapping
    public ResponseEntity<FlashCardDetailDto> createFlashCard(@RequestBody @Valid FlashCardRequestDto dto){
        return new ResponseEntity<>(service.addFlashCard(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{flashcardId}")
    public ResponseEntity<FlashCardDetailDto> updateFlashCard(@RequestBody FlashCardUpdateDto dto, @PathVariable long flashcardId){
        return new ResponseEntity<>(service.updateFlashCard(dto, flashcardId), HttpStatus.OK);
    }

    @DeleteMapping("/{flashcardId}")
    public ResponseEntity<Void> deleteFlashCard(@PathVariable long flashcardId){
        service.deleteById(flashcardId);
        return ResponseEntity.noContent().build();
    }
}
