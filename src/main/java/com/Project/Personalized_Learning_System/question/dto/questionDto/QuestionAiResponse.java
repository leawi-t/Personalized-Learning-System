package com.Project.Personalized_Learning_System.question.dto.questionDto;

import com.Project.Personalized_Learning_System.question.dto.choiceDto.ChoiceAiResponse;
import com.Project.Personalized_Learning_System.question.model.QuestionType;

import java.util.List;

public record QuestionAiResponse(
        String content,
        String explanation,
        QuestionType questionType,
        List<ChoiceAiResponse> choices
) {}
