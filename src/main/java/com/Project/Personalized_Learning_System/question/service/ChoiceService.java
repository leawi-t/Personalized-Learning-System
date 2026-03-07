package com.Project.Personalized_Learning_System.question.service;

import com.Project.Personalized_Learning_System.common.exception.customException.IllegalOperationException;
import com.Project.Personalized_Learning_System.common.exception.customException.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.question.mapper.ChoiceMapper;
import com.Project.Personalized_Learning_System.question.dto.choiceDto.ChoiceResponseDto;
import com.Project.Personalized_Learning_System.question.dto.choiceDto.CreateChoiceDto;
import com.Project.Personalized_Learning_System.question.dto.choiceDto.UpdateChoiceDto;
import com.Project.Personalized_Learning_System.question.model.Choice;
import com.Project.Personalized_Learning_System.question.model.Question;
import com.Project.Personalized_Learning_System.question.repository.ChoiceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoiceService {

    private final ChoiceRepo repo;
    private final ChoiceMapper choiceMapper;
    private final QuestionService questionService;


    public List<ChoiceResponseDto> getChoiceByQuestion(long questionId){
        return choiceMapper.toResponseList(repo.findByQuestionId(questionId));
    }

    public ChoiceResponseDto addChoice(CreateChoiceDto dto){
        Question question = questionService.getQuestionEntityById(dto.questionId());

        if (question.getChoices().size() > 6){
            throw new IllegalOperationException("Question can hava a maximum of 6 choices");
        }

        Choice choice = choiceMapper.toEntity(dto);
        choice.setQuestion(question);
        return choiceMapper.toResponse(repo.save(choice));
    }

    public ChoiceResponseDto updateChoice(UpdateChoiceDto updateChoiceDto, long choiceId){
        Choice choice = repo.findById(choiceId).
                orElseThrow(()->new ResourceNotFoundException("Choice not found"));
        choiceMapper.updateChoice(updateChoiceDto, choice);
        return choiceMapper.toResponse(repo.save(choice));
    }

    @Transactional
    public void deleteChoice(long id){
        Choice choice = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Choice not Found"));
        Question question = choice.getQuestion();

        if (question.getChoices().size()<=2){
            throw new IllegalOperationException("Multiple choice questionText must have at least 2 questions");
        }

        repo.deleteById(id);
    }
}
