package com.Project.Personalized_Learning_System.note.service;

import com.Project.Personalized_Learning_System.flashCard.FlashCard;
import com.Project.Personalized_Learning_System.flashCard.FlashCardMapper;
import com.Project.Personalized_Learning_System.flashCard.FlashCardRepo;
import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardAiResponse;
import com.Project.Personalized_Learning_System.note.Note;
import com.Project.Personalized_Learning_System.note.NoteRepo;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionAiResponse;
import com.Project.Personalized_Learning_System.question.mapper.QuestionMapper;
import com.Project.Personalized_Learning_System.question.model.Choice;
import com.Project.Personalized_Learning_System.question.model.Question;
import com.Project.Personalized_Learning_System.question.repository.QuestionRepo;
import com.Project.Personalized_Learning_System.topic.Topic;
import com.Project.Personalized_Learning_System.topic.TopicRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.StructuredOutputValidationAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteAiService {

    private final NoteService service;
    private final ChatClient chatClient;
    private final NoteRepo noteRepo;
    private final FlashCardMapper flashCardMapper;
    private final FlashCardRepo flashCardRepo;
    private final TopicRepo topicRepo;
    private final QuestionRepo questionRepo;
    private final QuestionMapper questionMapper;

    @Transactional
    public String summarize(Long noteId){
        Note note = service.getNoteEntityById(noteId);

        if (note.getSummary()!=null){
            return note.getSummary();
        }

        String fileContent = readFileContent(note.getFilePath());

        String summary = chatClient.prompt()
                .system("You are a helpful academic assistant. Summarize the following study notes in 3 concise bullet points.")
                .user(fileContent)
                .call()
                .content();

        note.setSummary(summary);
        noteRepo.save(note);
        return summary;
    }

    @Transactional
    public List<FlashCardAiResponse> generateFlashcards(Long noteId) {
        Note note = service.getNoteEntityById(noteId);
        String fileContent = readFileContent(note.getFilePath());

        List<FlashCardAiResponse> flashCardDtos = chatClient.prompt()
                .system("""
                You are an academic expert. Based on the provided study notes, 
                generate 5 high-quality flashcards. 
                - The 'question' should test a key concept.
                - The 'answer' should be concise.
                - The 'explanation' should provide context or 'why' it matters.
                """)
                .user(fileContent)
                .call()
                .entity(new ParameterizedTypeReference<List<FlashCardAiResponse>>() {});

        List<FlashCard> flashCards = flashCardMapper.toEntityAI(flashCardDtos);
        Topic topic = note.getTopic();

        for (FlashCard card : flashCards) {
            card.setTopic(note.getTopic());
            card.setSourceNote(note);
            note.addFlashCard(card);
        }

        topicRepo.save(topic);
        noteRepo.save(note);
        flashCardRepo.saveAll(flashCards);

        return flashCardDtos;
    }

    @Transactional
    public List<QuestionAiResponse> generateQuestions(Long noteId) {
        Note note = service.getNoteEntityById(noteId);
        String fileContent = readFileContent(note.getFilePath());
        var typeReference = new ParameterizedTypeReference<List<QuestionAiResponse>>() {};

        List<QuestionAiResponse> responses = chatClient.prompt()
                .system("""
                    You are an academic examiner. Generate 3 questions based on the notes.
                    For each question, select a 'questionType' from: [TRUE_FALSE, MULTIPLE_CHOICE].
    
                    Rules for MULTIPLE_CHOICE:
                         - Provide exactly 4 choices.
                         - Exactly one choice must have isCorrect: true.
    
                    Rules for TRUE_FALSE:
                        - Provide exactly 2 choices: "True" and "False".
                        - Mark the correct one with isCorrect: true.
                """)
                .user(fileContent)
                .call()
                .entity(typeReference);

        Topic topic = note.getTopic();

        List<Question> questions = responses.stream().map(resp -> {
            Question q = new Question();
            q.setQuestionText(resp.content());
            q.setQuestionType(resp.questionType()); // Maps automatically if names match
            q.setExplanation(resp.explanation());
            q.setSourceNote(note);
            q.setTopic(topic);

            resp.choices().forEach(cResp -> {
                Choice choice = new Choice();
                choice.setText(cResp.text());
                choice.setCorrect(cResp.isCorrect());
                q.addChoice(choice);
            });

            return q;
        }).toList();

        for (Question q: questions){
            for (Choice c: q.getChoices()){
                c.setQuestion(q);
            }
        }

        questionRepo.saveAll(questions);
        return responses;
    }

    public String readFileContent(String filePath) {
        try {
            Resource resource = new FileSystemResource(filePath);
            TikaDocumentReader reader = new TikaDocumentReader(resource);
            return reader.get().stream()
                    .map(Document::getText)
                    .collect(Collectors.joining("\n"));

        } catch (Exception e) {
            throw new RuntimeException("AI failed to read document: " + e.getMessage());
        }
    }

}
