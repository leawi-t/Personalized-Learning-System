package com.Project.Personalized_Learning_System.config;

import com.Project.Personalized_Learning_System.dto.choiceDto.CreateChoiceDto;
import com.Project.Personalized_Learning_System.dto.flashCardDto.FlashCardRequestDto;
import com.Project.Personalized_Learning_System.dto.questionDto.*;
import com.Project.Personalized_Learning_System.dto.subjectDto.*;
import com.Project.Personalized_Learning_System.dto.topicDto.*;
import com.Project.Personalized_Learning_System.dto.userDto.*;
import com.Project.Personalized_Learning_System.repository.UserRepo;
import com.Project.Personalized_Learning_System.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Profile("!test") // Prevents this from running during actual unit tests
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepo;
    private final UserService userService;
    private final SubjectService subjectService;
    private final TopicService topicService;
    private final FlashCardService flashCardService;
    private final QuestionService questionService;

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() == 0) {
            System.out.println("--- Starting Data Initialization ---");

            // 1. Create a User
            UserRequestDto userDto = new UserRequestDto("john_doe", "john@example.com", "password123");
            UserDetailsDto user = userService.registerUser(userDto);
            System.out.println("Created User: " + user.username());

            // 2. Create a Subject for that User
            SubjectRequestDto subjectDto = new SubjectRequestDto(user.id(), "Computer Science", "Core concepts of programming");
            SubjectDetailDto subject = subjectService.addSubject(subjectDto);
            System.out.println("Created Subject: " + subject.name());

            // 3. Create a Topic for that Subject
            TopicRequestDto topicDto = new TopicRequestDto(subject.id(), "Java Persistence API", "Learning about Hibernate and JPA");
            TopicDetailDto topic = topicService.addTopic(topicDto);
            System.out.println("Created Topic: " + topic.name());

            // 4. Create a Flashcard for that Topic
            FlashCardRequestDto fcDto = new FlashCardRequestDto(
                    topic.id(),
                    "What is @MappedSuperclass?",
                    "It allows entities to inherit properties from a base class without being an entity itself.",
                    Set.of("JPA", "Hibernate", "Pro-Tip"),
                    3
            );

            flashCardService.addFlashCard(fcDto);
            System.out.println("Created Flashcard for Topic: " + topic.name());

            // 5. Create a Question with Choices
            List<CreateChoiceDto> choices = List.of(
                    new CreateChoiceDto(null, "Java Database Connectivity", false),
                    new CreateChoiceDto(null, "Java Persistence API", true),
                    new CreateChoiceDto(null, "Just Powerful Applications", false)
            );

            QuestionRequestDto qDto = new QuestionRequestDto(
                    topic.id(),
                    "What does JPA stand for?",
                    "MULTIPLE_CHOICE",
                    2,
                    choices
            );

            questionService.addQuestion(qDto);
            System.out.println("Created Question with 3 choices.");

            System.out.println("--- Data Initialization Complete ---");
        }
    }
}
