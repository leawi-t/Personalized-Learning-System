package com.Project.Personalized_Learning_System.note.controller;

import com.Project.Personalized_Learning_System.note.service.NoteAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NoteAiController {

    private final NoteAiService service;

    @GetMapping("/{noteId}/summarize")
    public ResponseEntity<String> summarize(@PathVariable Long noteId){
        return ResponseEntity.ok(service.summarize(noteId));
    }
}
