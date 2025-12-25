package com.Project.Personalized_Learning_System.mapper;

import com.Project.Personalized_Learning_System.dto.categoryDto.*;
import com.Project.Personalized_Learning_System.model.Category;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper(componentModel = "spring", uses = TopicMapper.class)
public interface CategoryMapper {

    CategoryResponseDto toResponse(Category category);

    List<CategoryResponseDto> categoriesToResponse(List<Category> categories);

    CategoryDetailDto toDetail(Category category);

    Category toEntity(CreateCategoryDto createCategoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategory(CategoryUpdateDto categoryUpdateDto, @MappingTarget Category category);
}
