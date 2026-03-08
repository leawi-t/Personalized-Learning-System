package com.Project.Personalized_Learning_System.note.service;

import com.Project.Personalized_Learning_System.note.Note;
import com.Project.Personalized_Learning_System.note.NoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public String readFileContent(String filePath){
        try {
            return Files.readString(Path.of(URI.create("file:///" + filePath.replace("\\", "/"))));
        } catch (Exception e) {
            try {
                return Files.readString(Paths.get(filePath));
            } catch (IOException ex) {
                throw new RuntimeException("AI could not read the note file: " + ex.getMessage());
            }
        }
    }
}
