package com.Project.Personalized_Learning_System.question.mapper;

import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionAiResponse;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionRequestDto;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionResponseDto;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionUpdateDto;
import com.Project.Personalized_Learning_System.question.model.Question;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = ChoiceMapper.class)
public interface QuestionMapper {

    QuestionResponseDto toResponse(Question question);

    List<QuestionAiResponse> toAiResponse(List<Question> question);

    Question toEntity(QuestionRequestDto questionRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateQuestion(QuestionUpdateDto questionUpdateDto, @MappingTarget Question question);
}
