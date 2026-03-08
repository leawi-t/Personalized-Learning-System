package com.Project.Personalized_Learning_System.note.service;

import com.Project.Personalized_Learning_System.note.Note;
import com.Project.Personalized_Learning_System.note.NoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteAiService {

    private final NoteService service;
    private final ChatClient chatClient;
    private final NoteRepo repo;

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
        repo.save(note);
        return summary;
    }

    public String readFileContent(String filePath) {
        try {
            // 1. Convert the file path to a Spring Resource
            // Using "file:" prefix ensures it looks at your local disk
            Resource resource = new FileSystemResource(filePath);

            // 2. Initialize the Tika reader with the resource
            TikaDocumentReader reader = new TikaDocumentReader(resource);

            // 3. Read the document(s) and join the text
            // Tika returns a List of Documents (usually 1 for standard files)
            return reader.get().stream()
                    .map(Document::getText)
                    .collect(Collectors.joining("\n"));

        } catch (Exception e) {
            throw new RuntimeException("AI failed to read document: " + e.getMessage());
        }
    }
}
