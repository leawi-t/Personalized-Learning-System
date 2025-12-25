package com.Project.Personalized_Learning_System.mapper;

import com.Project.Personalized_Learning_System.dto.questionDto.*;
import com.Project.Personalized_Learning_System.model.Question;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = ChoiceMapper.class)
public interface QuestionMapper {

    List<QuestionDetailsDto> questionToDetail(List<Question> questions);

    QuestionDetailsDto toDetails(Question question);

    Question toEntity(CreateQuestionDto createQuestionDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateQuestion(QuestionUpdateDto questionUpdateDto, @MappingTarget Question question);
}
