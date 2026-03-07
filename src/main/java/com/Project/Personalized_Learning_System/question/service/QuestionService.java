package com.Project.Personalized_Learning_System.question.service;

import com.Project.Personalized_Learning_System.common.exception.customException.IllegalOperationException;
import com.Project.Personalized_Learning_System.common.exception.customException.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.question.mapper.QuestionMapper;
import com.Project.Personalized_Learning_System.question.dto.questionDto.FilterQuestionDto;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionRequestDto;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionResponseDto;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionUpdateDto;
import com.Project.Personalized_Learning_System.question.model.Choice;
import com.Project.Personalized_Learning_System.question.model.Question;
import com.Project.Personalized_Learning_System.question.model.QuestionType;
import com.Project.Personalized_Learning_System.topic.Topic;
import com.Project.Personalized_Learning_System.question.repository.QuestionRepo;
import com.Project.Personalized_Learning_System.topic.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepo repo;
    private final QuestionMapper questionMapper;
    private final TopicService topicService;

    public Question getQuestionEntityById(long id){
        return repo.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Question not found"));
    }

    public Page<QuestionResponseDto> getQuestions(FilterQuestionDto dto,
                                                  Pageable pageable){
        QuestionType questionType = (dto.type() != null) ? QuestionType.valueOf(dto.type().toUpperCase()) : null;

        Specification<Question> spec = Specification.where(QuestionSpecs.hasTopicId(dto.topicId()))
                .and(QuestionSpecs.hasQuestionText(dto.questionText()))
                .and(QuestionSpecs.hasDifficulty(dto.min(), dto.max()))
                .and(QuestionSpecs.hasTag(dto.tag()))
                .and(QuestionSpecs.hasDate(dto.start(), dto.end()))
                .and(QuestionSpecs.hasType(questionType));

        return repo.findAll(spec, pageable).map(questionMapper::toResponse);
    }

    @Transactional
    public QuestionResponseDto addQuestion(QuestionRequestDto dto){
        if (dto.questionType().equalsIgnoreCase("TRUE_FALSE") && dto.choices().size() > 2){
            throw new IllegalOperationException("True/false question can have only two questions");
        }

        Topic topic = topicService.getTopicEntityById(dto.topicId());
        Question question = questionMapper.toEntity(dto);

        question.setTopic(topic);
        for (Choice c : question.getChoices()) {
            c.setQuestion(question);
        }

        return questionMapper.toResponse(repo.save(question));
    }

    @Transactional
    public QuestionResponseDto updateQuestion(QuestionUpdateDto questionUpdateDto, long questionId){
        Question question = repo.findById(questionId).orElseThrow(()-> new ResourceNotFoundException("Question not found"));
        questionMapper.updateQuestion(questionUpdateDto, question);

        for (Choice c : question.getChoices()) {
            c.setQuestion(question);
        }

        return questionMapper.toResponse(repo.save(question));
    }

    @Transactional
    public void deleteQuestionById(long id){
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Question not found");
        repo.deleteById(id);
    }

    public QuestionResponseDto getQuestionById(long questionId) {
        return questionMapper.toResponse(repo.findById(questionId).
                orElseThrow(() -> new ResourceNotFoundException("Question was not found")));
    }
}

