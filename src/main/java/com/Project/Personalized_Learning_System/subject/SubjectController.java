package com.Project.Personalized_Learning_System.subject;

import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectDetailDto;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectRequestDto;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectResponseDto;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<PagedModel<SubjectResponseDto>> getSubjects(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end,
            Pageable pageable
    ){
        Page<SubjectResponseDto> page = subjectService.getSubjects(pageable, userId, name, description, start, end);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDetailDto> getSubjectById(
            @PathVariable long subjectId) {

        return ResponseEntity.ok(subjectService.getSubjectById(subjectId));
    }

    @PostMapping
    public ResponseEntity<SubjectDetailDto> createSubject(@RequestBody @Valid SubjectRequestDto dto){
        return new ResponseEntity<>(subjectService.addSubject(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectDetailDto> updateSubject(
            @RequestBody @Valid SubjectUpdateDto dto,
            @PathVariable long subjectId) {

        return ResponseEntity.ok(subjectService.updateSubject(dto, subjectId));
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> deleteById(@PathVariable long subjectId){
        subjectService.deleteSubject(subjectId);
        return ResponseEntity.noContent().build();
    }
}

