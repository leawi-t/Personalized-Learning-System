package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.flashCardDto.FlashCardDetailDto;
import com.Project.Personalized_Learning_System.dto.flashCardDto.FlashCardUpdateDto;
import com.Project.Personalized_Learning_System.service.FlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flashcards")
public class FlashCardController {

    private final FlashCardService flashCardService;

    @Autowired
    public FlashCardController(FlashCardService flashCardService){
        this.flashCardService = flashCardService;
    }

    @GetMapping
    public ResponseEntity<List<FlashCardDetailDto>> getAllFlashCards(){
        return new ResponseEntity<>(flashCardService.getAllFlashCards(), HttpStatus.OK);
    }

    @GetMapping("/{flashcardId}")
    public ResponseEntity<FlashCardDetailDto> getFlashCardById(@PathVariable long flashcardId){
        return new ResponseEntity<>(flashCardService.getFlashCardById(flashcardId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlashCardDetailDto>> searchFlashCard(@RequestParam String keyword){
        return new ResponseEntity<>(flashCardService.searchFlashCard(keyword), HttpStatus.OK);
    }

    @PutMapping("/{flashcardId}")
    public ResponseEntity<FlashCardDetailDto> updateFlashCard(@RequestBody FlashCardUpdateDto dto, @PathVariable long flashcardId){
        return new ResponseEntity<>(flashCardService.updateFlashCard(dto, flashcardId), HttpStatus.OK);
    }

    @DeleteMapping("/{flashcardId}")
    public ResponseEntity<Void> deleteFlashCard(@PathVariable long flashcardId){
        flashCardService.deleteById(flashcardId);
        return ResponseEntity.noContent().build();
    }


}
