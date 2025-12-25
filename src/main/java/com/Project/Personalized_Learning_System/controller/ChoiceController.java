package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.choiceDto.ChoiceDetailDto;
import com.Project.Personalized_Learning_System.dto.choiceDto.UpdateChoiceDto;
import com.Project.Personalized_Learning_System.service.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/choices")
public class ChoiceController {

    private final ChoiceService service;

    @Autowired
    public ChoiceController(ChoiceService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ChoiceDetailDto>> getAllChoices(){
        return new ResponseEntity<>(service.getAllChoices(), HttpStatus.OK);
    }

    @GetMapping("/{choiceId}")
    public ResponseEntity<ChoiceDetailDto> getChoiceById(@PathVariable long choiceId){
        return new ResponseEntity<>(service.getChoiceById(choiceId), HttpStatus.OK);
    }

    @PutMapping("/{choiceId}")
    public ResponseEntity<ChoiceDetailDto> updateChoice(@RequestBody UpdateChoiceDto dto, @PathVariable long choiceId){
        return new ResponseEntity<>(service.updateChoice(dto, choiceId), HttpStatus.OK);
    }

    @DeleteMapping("/{choiceId}")
    public ResponseEntity<Void> deleteChoice(@PathVariable long choiceId){
        service.deleteChoice(choiceId);
        return ResponseEntity.noContent().build();
    }
}
