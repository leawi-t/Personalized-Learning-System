package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.dto.choiceDto.ChoiceDetailDto;
import com.Project.Personalized_Learning_System.dto.choiceDto.CreateChoiceDto;
import com.Project.Personalized_Learning_System.dto.choiceDto.UpdateChoiceDto;
import com.Project.Personalized_Learning_System.exception.IllegalOperationException;
import com.Project.Personalized_Learning_System.exception.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.mapper.ChoiceMapper;
import com.Project.Personalized_Learning_System.model.Choice;
import com.Project.Personalized_Learning_System.model.Question;
import com.Project.Personalized_Learning_System.model.User;
import com.Project.Personalized_Learning_System.repository.ChoiceRepo;
import com.Project.Personalized_Learning_System.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoiceService {

    private final ChoiceRepo repo;
    private final ChoiceMapper choiceMapper;
    private final QuestionService questionService;

    @Autowired
    public ChoiceService(ChoiceRepo repo, ChoiceMapper choiceMapper,
                         QuestionService questionService) {
        this.repo = repo;
        this.choiceMapper = choiceMapper;
        this.questionService = questionService;
    }

    public List<ChoiceDetailDto> getAllChoices(){
        return choiceMapper.choiceToDetail(repo.findAll());
    }

    public ChoiceDetailDto getChoiceById(long choiceId){
        return choiceMapper.toDetail(repo.findById(choiceId)
                .orElseThrow(()-> new ResourceNotFoundException("Choice Not Found")));
    }

    public List<ChoiceDetailDto> getChoiceByQuestion(long questionId){
        return choiceMapper.choiceToDetail(repo.findByQuestionId(questionId));
    }

    public ChoiceDetailDto addChoice(CreateChoiceDto createChoiceDto, long questionId){
        Question question = questionService.getQuestionEntityById(questionId);
        Choice choice = choiceMapper.toEntity(createChoiceDto);
        choice.setQuestion(question);
        return choiceMapper.toDetail(repo.save(choice));
    }

    public ChoiceDetailDto updateChoice(UpdateChoiceDto updateChoiceDto, long choiceId){
        Choice choice = repo.findById(choiceId).
                orElseThrow(()->new ResourceNotFoundException("Choice not found"));
        choiceMapper.updateChoice(updateChoiceDto, choice);
        return choiceMapper.toDetail(repo.save(choice));
    }

    public void deleteChoice(long id){
        Choice choice = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Choice not Found"));
        Question question = choice.getQuestion();

        // making sure that a question can have minimum of two choices
        if (question.getChoices().size()<=2){
            throw new IllegalOperationException("Multiple choice question must have at least 2 questions");
        }

        repo.deleteById(id);
    }


}
