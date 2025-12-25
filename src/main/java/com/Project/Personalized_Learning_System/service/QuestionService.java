package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.dto.questionDto.*;
import com.Project.Personalized_Learning_System.exception.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.mapper.QuestionMapper;
import com.Project.Personalized_Learning_System.model.Choice;
import com.Project.Personalized_Learning_System.model.Question;
import com.Project.Personalized_Learning_System.model.QuestionType;
import com.Project.Personalized_Learning_System.model.Topic;
import com.Project.Personalized_Learning_System.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepo repo;
    private final QuestionMapper questionMapper;
    private final TopicService topicService;

    @Autowired
    public QuestionService(QuestionRepo repo, QuestionMapper questionMapper, TopicService topicService){
        this.repo = repo;
        this.questionMapper = questionMapper;
        this.topicService = topicService;
    }

    public Question getQuestionEntityById(long id){
        return repo.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Question not found"));
    }

    public List<QuestionDetailsDto> getAllQuestions(){
        return questionMapper.questionToDetail(repo.findAll());
    }

    public QuestionDetailsDto getQuestionById(long id){
        return questionMapper.toDetails(repo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Question not Found")));
    }

    public List<QuestionDetailsDto> findByTopic (long topicId){
        return questionMapper.questionToDetail(repo.findByTopicId(topicId));
    }

    public List<QuestionDetailsDto> filterByDifficulty (int lDifficulty, int hDifficulty){
        return questionMapper.questionToDetail(repo.filterByDifficulty(lDifficulty, hDifficulty));
    }

    public List<QuestionDetailsDto> findByType(QuestionType type){
        return questionMapper.questionToDetail(repo.findByQuestionType(type));
    }

    public QuestionDetailsDto addQuestion(CreateQuestionDto dto, long topicId){
        Topic topic = topicService.getTopicEntityById(topicId);
        Question question = questionMapper.toEntity(dto);
        question.setTopic(topic);

        for (Choice c : question.getChoices()) {
            c.setQuestion(question);
        }

        return questionMapper.toDetails(repo.save(question));
    }


    public QuestionDetailsDto updateQuestion(QuestionUpdateDto questionUpdateDto, long questionId){
        Question question = repo.findById(questionId).orElseThrow(()-> new ResourceNotFoundException("Question not found"));
        questionMapper.updateQuestion(questionUpdateDto, question);

        for (Choice c : question.getChoices()) {
            c.setQuestion(question);
        }

        return questionMapper.toDetails(repo.save(question));
    }

    public void deleteQuestionById(long id){
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Question not found");
        repo.deleteById(id);
    }
}

