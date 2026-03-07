package com.Project.Personalized_Learning_System.subject;

import com.Project.Personalized_Learning_System.common.exception.customException.DuplicateEntityException;
import com.Project.Personalized_Learning_System.common.exception.customException.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectDetailDto;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectRequestDto;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectResponseDto;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectUpdateDto;
import com.Project.Personalized_Learning_System.user.User;
import com.Project.Personalized_Learning_System.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepo repo;
    private final SubjectMapper subjectMapper;
    private final UserService userService;

    public Subject getSubjectEntityById(long id){
        return repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Subject not found"));
    }

    public Page<SubjectResponseDto> getSubjects(Pageable pageable, Long userId, String name, String description,
                                                LocalDateTime start, LocalDateTime end){
        Specification<Subject> spec = Specification.where(SubjectSpecs.hasUserId(userId))
                .and(SubjectSpecs.hasDescription(description))
                .and(SubjectSpecs.hasName(name))
                .and(SubjectSpecs.dateBetween(start, end));

        return repo.findAll(spec, pageable).map(subjectMapper::toResponse);
    }

    public SubjectDetailDto getSubjectById(long subjectId){
        return subjectMapper.toDetail(repo.findById(subjectId)
                .orElseThrow(()-> new ResourceNotFoundException("Subject was not found")));
    }

    @Transactional
    public SubjectDetailDto addSubject(SubjectRequestDto dto){
        User user = userService.getUserEntityById(dto.userId());

        if (repo.existsByNameAndUserId(dto.name(), dto.userId())){
            throw new DuplicateEntityException("Subject with name: " + dto.name() + " Already exists");
        }

        Subject subject = subjectMapper.toEntity(dto);
        subject.setUser(user);

        return subjectMapper.toDetail(repo.save(subject));
    }

    @Transactional
    public SubjectDetailDto updateSubject(SubjectUpdateDto dto, long subjectId){
        Subject subject = repo.findById(subjectId)
                .orElseThrow(()-> new ResourceNotFoundException("Subject does not exist"));

        if (dto.name() != null && !dto.name().equals(subject.getName())) {
            if (repo.existsByNameAndUserId(dto.name(), subject.getUser().getId())) {
                throw new DuplicateEntityException("Subject with name: " + dto.name() + " already exists");
            }
        }

        subjectMapper.updateSubject(dto, subject);
        return subjectMapper.toDetail(repo.save(subject));
    }

    @Transactional
    public void deleteSubject(long subjectId){
        if (!repo.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found");
        }
        repo.deleteById(subjectId);
    }
}
