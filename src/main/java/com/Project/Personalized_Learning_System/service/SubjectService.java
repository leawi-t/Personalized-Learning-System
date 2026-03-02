package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.dto.subjectDto.*;
import com.Project.Personalized_Learning_System.exception.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.mapper.SubjectMapper;
import com.Project.Personalized_Learning_System.model.Subject;
import com.Project.Personalized_Learning_System.model.User;
import com.Project.Personalized_Learning_System.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepo repo;
    private final SubjectMapper subjectMapper;
    private final UserService userService;

    @Autowired
    public SubjectService(SubjectRepo repo, SubjectMapper mapper, UserService userService) {
        this.repo = repo;
        this.subjectMapper = mapper;
        this.userService = userService;
    }

    public List<SubjectResponseDto> getAllCategories(){
        return subjectMapper.toResponseList(repo.findAll());
    }

    public Subject getCategoryEntityById(long id){
        return repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    public SubjectDetailDto getCategoryById(long categoryId){
        Subject subject = repo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist in the database"));
        return subjectMapper.toDetail(subject);
    }

    public List<SubjectResponseDto> searchCategory(String keyword){
        List<Subject> categories = repo.searchSubject(keyword);
        return subjectMapper.toResponseList(categories);
    }

    public List<SubjectResponseDto> getCategoryByUser(long userId){
        List<Subject> categories = repo.findByUserId(userId);
        return subjectMapper.toResponseList(categories);
    }

    public SubjectDetailDto findByUserIdAndId(long userId, long categoryId) {
        return subjectMapper.toDetail(
                repo.findByUserIdAndId(userId, categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"))
        );
    }

    public SubjectDetailDto addCategory(SubjectRequestDto subjectRequestDto, long userId){
        User user = userService.getUserEntityById(userId);
        Subject subject = subjectMapper.toEntity(subjectRequestDto);
        subject.setUser(user);

        return subjectMapper.toDetail(repo.save(subject));
    }

    public SubjectDetailDto updateCategory(SubjectUpdateDto dto, long categoryId){
        Subject subject = repo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist in the database"));
        subjectMapper.updateCategory(dto, subject);

        return subjectMapper.toDetail(repo.save(subject));
    }

    public void deleteCategory(long categoryId){
        if (!repo.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found");
        }
        repo.deleteById(categoryId);
    }
}
