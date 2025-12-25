package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.dto.noteDto.*;
import com.Project.Personalized_Learning_System.exception.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.mapper.NoteMapper;
import com.Project.Personalized_Learning_System.model.Note;
import com.Project.Personalized_Learning_System.model.Topic;
import com.Project.Personalized_Learning_System.repository.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class NoteService {

    NoteRepo repo;
    NoteMapper mapper;
    TopicService topicService;
    LocalFileStorageService fileStorageService;

    @Autowired
    public NoteService(NoteRepo repo, NoteMapper mapper, TopicService topicService, LocalFileStorageService fileStorageService){
        this.repo= repo;
        this.topicService = topicService;
        this.mapper = mapper;
        this.fileStorageService = fileStorageService;
    }

    public NoteDetailDto getNoteById(long id){
        return mapper.toDetails(repo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Note not found")));
    }

    public List<NoteResponseDto> getNoteByTopic(long topicId){
        return mapper.noteToResponse(repo.findByTopicId(topicId));
    }

    public NoteDetailDto addNote(CreateNoteDto createNoteDto, long topicId, MultipartFile file){
        Topic topic = topicService.getTopicEntityById(topicId);
        Note note = new Note();
        String filePath = fileStorageService.saveFile(file);

        note.setName(createNoteDto.name());
        note.setDescription(createNoteDto.description());
        note.setTopic(topic);

        note.setFileName(file.getOriginalFilename());
        note.setFileSize(file.getSize());
        note.setFileType(file.getContentType());
        note.setFilePath(filePath);

        return mapper.toDetails(repo.save(note));
    }

    public NoteDetailDto updateNote(NoteUpdateDto noteUpdateDto, long noteId, MultipartFile file){
        Note note = repo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Note not found"));
        note.setName(noteUpdateDto.name());
        note.setDescription(noteUpdateDto.description());

        if (file!=null && !file.isEmpty()){

            if (note.getFilePath() != null) {
                fileStorageService.delete(note.getFilePath());
            }

            String newFilePath = fileStorageService.saveFile(file);

            note.setFileName(file.getOriginalFilename());
            note.setFileType(file.getContentType());
            note.setFilePath(newFilePath);
            note.setFileSize(file.getSize());
        }

        return mapper.toDetails(repo.save(note));
    }

    public void deleteNoteById(long id){
        Note note = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

        fileStorageService.delete(note.getFilePath());
        repo.delete(note);
    }

    public ResponseEntity<Resource> downloadNote(long noteId) {
        Note note = repo.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

        Resource resource = fileStorageService.loadAsResource(note.getFilePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(note.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + note.getFileName() + "\"")
                .body(resource);
    }



}
