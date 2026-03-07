package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.noteDto.*;
import com.Project.Personalized_Learning_System.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<PagedModel<NoteResponseDto>> getNotes(
            @RequestParam(required = false) Long topicId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end,
            Pageable pageable
    ){
        Page<NoteResponseDto> page = noteService.getNotes(topicId, name, description, start, end, pageable);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDetailDto> getNoteById(@PathVariable long noteId){
        return new ResponseEntity<>(noteService.getNoteById(noteId), HttpStatus.OK);
    }

    @GetMapping("/{noteId}/download")
    public ResponseEntity<Resource> downloadNote(@PathVariable long noteId){
        return noteService.downloadNote(noteId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NoteDetailDto> createNote(@RequestPart("dto") @Valid NoteRequestDto dto,
                                                    @RequestPart("file") MultipartFile file) {
        System.out.println("Controller reached");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(noteService.addNote(dto, file));
    }

    @PutMapping(value = "/{noteId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NoteDetailDto> updateNote(
            @RequestPart("dto") @Valid NoteUpdateDto dto,
            @PathVariable long noteId,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        return ResponseEntity.ok(noteService.updateNote(dto, noteId, file));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable long noteId){
        noteService.deleteNoteById(noteId);
        return ResponseEntity.noContent().build();
    }
}
