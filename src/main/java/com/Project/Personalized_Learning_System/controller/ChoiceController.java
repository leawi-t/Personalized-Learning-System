package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.choiceDto.ChoiceResponseDto;
import com.Project.Personalized_Learning_System.dto.choiceDto.CreateChoiceDto;
import com.Project.Personalized_Learning_System.dto.choiceDto.UpdateChoiceDto;
import com.Project.Personalized_Learning_System.service.ChoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/choices")
public class ChoiceController {

    private final ChoiceService service;

    @GetMapping
    public ResponseEntity<List<ChoiceResponseDto>> getChoiceByQuestion(@RequestParam long questionId){
        return ResponseEntity.ok(service.getChoiceByQuestion(questionId));
    }

    @PostMapping()
    public ResponseEntity<ChoiceResponseDto> createChoice(@RequestBody @Valid CreateChoiceDto dto){
        return new ResponseEntity<>(service.addChoice(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{choiceId}")
    public ResponseEntity<ChoiceResponseDto> updateChoice(@RequestBody @Valid UpdateChoiceDto dto, @PathVariable long choiceId){
        return new ResponseEntity<>(service.updateChoice(dto, choiceId), HttpStatus.OK);
    }

    @DeleteMapping("/{choiceId}")
    public ResponseEntity<Void> deleteChoice(@PathVariable long choiceId){
        service.deleteChoice(choiceId);
        return ResponseEntity.noContent().build();
    }
}
