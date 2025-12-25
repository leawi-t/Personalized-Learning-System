package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.noteDto.*;
import com.Project.Personalized_Learning_System.service.NoteService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDetailDto> getNoteById(@PathVariable long noteId){
        return new ResponseEntity<>(noteService.getNoteById(noteId), HttpStatus.OK);
    }

    @GetMapping("/{noteId}/download")
    public ResponseEntity<Resource> downloadNote(@PathVariable long noteId){
        return noteService.downloadNote(noteId);
    }

    @PutMapping("{noteId}")
    public ResponseEntity<NoteDetailDto> updateNote(@RequestBody NoteUpdateDto dto, @PathVariable long noteId, @RequestPart MultipartFile file){
        return new ResponseEntity<>(noteService.updateNote(dto, noteId, file), HttpStatus.OK);
    }

    @DeleteMapping("{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable long noteId){
        noteService.deleteNoteById(noteId);
        return ResponseEntity.noContent().build();
    }
}
