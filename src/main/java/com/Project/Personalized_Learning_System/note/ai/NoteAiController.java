package com.Project.Personalized_Learning_System.note.ai;

import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardAiResponse;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionAiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NoteAiController {

    private final NoteAiService service;

    @GetMapping("/{noteId}/summarize")
    public ResponseEntity<String> summarize(@PathVariable Long noteId){
        return ResponseEntity.ok(service.summarize(noteId));
    }

    @GetMapping("/{noteId}/flashCards")
    public ResponseEntity<List<FlashCardAiResponse>> generateFlashCards(@PathVariable Long noteId){
        return ResponseEntity.ok(service.generateFlashcards(noteId));
    }

    @GetMapping("/{noteId}/questions")
    public ResponseEntity<List<QuestionAiResponse>> generateQuestions(@PathVariable Long noteId){
        return ResponseEntity.ok(service.generateQuestions(noteId));
    }
}
