package com.Project.Personalized_Learning_System.note.service;

import com.Project.Personalized_Learning_System.flashCard.FlashCard;
import com.Project.Personalized_Learning_System.flashCard.FlashCardMapper;
import com.Project.Personalized_Learning_System.flashCard.FlashCardRepo;
import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardAiResponse;
import com.Project.Personalized_Learning_System.note.Note;
import com.Project.Personalized_Learning_System.note.NoteRepo;
import com.Project.Personalized_Learning_System.topic.Topic;
import com.Project.Personalized_Learning_System.topic.TopicRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
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
            note.getFlashCards().add(card);
        }

        topicRepo.save(topic);
        noteRepo.save(note);
        flashCardRepo.saveAll(flashCards);

        return flashCardDtos;
    }
}
