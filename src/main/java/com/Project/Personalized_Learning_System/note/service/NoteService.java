package com.Project.Personalized_Learning_System.note.service;

import com.Project.Personalized_Learning_System.common.exception.customException.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.common.fileStorage.LocalFileStorageService;
import com.Project.Personalized_Learning_System.note.Note;
import com.Project.Personalized_Learning_System.note.NoteMapper;
import com.Project.Personalized_Learning_System.note.NoteRepo;
import com.Project.Personalized_Learning_System.note.noteDto.NoteDetailDto;
import com.Project.Personalized_Learning_System.note.noteDto.NoteRequestDto;
import com.Project.Personalized_Learning_System.note.noteDto.NoteResponseDto;
import com.Project.Personalized_Learning_System.note.noteDto.NoteUpdateDto;
import com.Project.Personalized_Learning_System.topic.Topic;
import com.Project.Personalized_Learning_System.topic.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

//TODO: validate the file that you receive using apache tika dependency
public class NoteService {

    private final NoteRepo repo;
    private final NoteMapper mapper;
    private final TopicService topicService;
    private final LocalFileStorageService fileStorageService;

    public Note getNoteEntityById(Long noteId){
        return repo.findById(noteId).orElseThrow(()->new ResourceNotFoundException("Note was not found"));
    }

    public Page<NoteResponseDto> getNotes(Long topicId, String name, String description,
                                          LocalDateTime start, LocalDateTime end, Pageable pageable){
        Specification<Note> spec = Specification.where(NoteSpecs.hasTopicId(topicId))
                .and(NoteSpecs.hasName(name))
                .and(NoteSpecs.hasDescription(description))
                .and(NoteSpecs.dateBetween(start, end));

        return repo.findAll(spec, pageable).map(mapper::toResponse);
    }

    public NoteDetailDto getNoteById(long noteId) {
        return mapper.toDetails(repo.findById(noteId)
                .orElseThrow(()->new ResourceNotFoundException("Note not found")));
    }

    public NoteDetailDto addNote(NoteRequestDto dto, MultipartFile file){
        Topic topic = topicService.getTopicEntityById(dto.topicId());
        Note note = new Note();
        String filePath = fileStorageService.saveFile(file);

        note.setName(dto.name());
        note.setDescription(dto.description());
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
