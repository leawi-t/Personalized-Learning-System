package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.dto.categoryDto.*;
import com.Project.Personalized_Learning_System.exception.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.mapper.CategoryMapper;
import com.Project.Personalized_Learning_System.model.Category;
import com.Project.Personalized_Learning_System.model.User;
import com.Project.Personalized_Learning_System.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo repo;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    @Autowired
    public CategoryService(CategoryRepo repo, CategoryMapper mapper, UserService userService) {
        this.repo = repo;
        this.categoryMapper = mapper;
        this.userService = userService;
    }

    public List<CategoryResponseDto> getAllCategories(){
        return categoryMapper.categoriesToResponse(repo.findAll());
    }

    public Category getCategoryEntityById(long id){
        return repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    public CategoryDetailDto getCategoryById(long categoryId){
        Category category = repo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist in the database"));
        return categoryMapper.toDetail(category);
    }

    public List<CategoryResponseDto> searchCategory(String keyword){
        List<Category> categories = repo.searchCategory(keyword);
        return categoryMapper.categoriesToResponse(categories);
    }

    public List<CategoryResponseDto> getCategoryByUser(long userId){
        List<Category> categories = repo.findByUserId(userId);
        return categoryMapper.categoriesToResponse(categories);
    }

    public CategoryDetailDto findByUserIdAndId(long userId, long categoryId) {
        return categoryMapper.toDetail(
                repo.findByUserIdAndId(userId, categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"))
        );
    }

    public CategoryDetailDto addCategory(CreateCategoryDto createCategoryDto, long userId){
        User user = userService.getUserEntityById(userId);
        Category category = categoryMapper.toEntity(createCategoryDto);
        category.setUser(user);

        return categoryMapper.toDetail(repo.save(category));
    }

    public CategoryDetailDto updateCategory(CategoryUpdateDto dto, long categoryId){
        Category category = repo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist in the database"));
        categoryMapper.updateCategory(dto, category);

        return categoryMapper.toDetail(repo.save(category));
    }

    public void deleteCategory(long categoryId){
        if (!repo.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found");
        }
        repo.deleteById(categoryId);
    }
}
