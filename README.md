🎓 Personalized Learning System

A "Smart" Backend infrastructure built with Spring Boot 4 and Spring AI. This system transforms static study materials (PDFs and Text files) into interactive learning tools by leveraging Large Language Models (LLMs) to generate summaries, flashcards, and practice questions.

🚀 Core AI Features
📄Intelligent Document Processing

- Multi-Format Support: Uses Apache Tika to extract clean text from .pdf and .txt files.
- Automated Summarization: Generates concise, 3-point academic summaries for every uploaded note to provide quick overviews.

🧠 Active Recall Tools
- AI Flashcard Generation: Analyzes note content to produce a list of key concepts and definitions formatted as flashcards.
- Dynamic Question Bank: Generates Multiple-Choice Questions (MCQs) with plausible distractors and detailed explanations for the correct answers.
- Structured Output: Utilizes Spring AI BeanOutputConverter to ensure AI responses are mapped directly into Java DTOs and persisted to MySQL.

🛠️ Technical Stack
-Framework: Spring Boot 4.0.0 (Jakarta EE 11)
-AI Integration: Spring AI 2.0.0-M2 (using spring-ai-starter-model-openai)
-LLM Provider: Groq (Llama 3.3 70B) for high-speed inference.
-Data Persistence: Spring Data JPA + MySQL 8.0.
-Parsing: Apache Tika for document text extraction.
-Tooling: Lombok, MapStruct (for DTO mapping), and Maven.

📂 Project Structure 

(AI Focus)Plaintextsrc/main/java/com/Project/Personalized_Learning_System/
├── common/
│   └── fileStorage/         # Handles Absolute Path storage for documents
├── modules/
│   ├── note/                # Core Entity: The "Source of Truth"
│   ├── ai/
│   │   ├── dto/             # AI Response Records (FlashcardAiResponse, etc.)
│   │   ├── NoteAiService.java    # The "Brain": Handles ChatClient prompts
│   │   └── NoteAiController.java # Endpoints for AI generation
│   ├── flashcard/           # AI-generated Flashcards
│   └── question/            # AI-generated MCQs with nested Choices


⚙️ ConfigurationTo run this project, you will need a Groq API Key (Free Tier). Add the following to your application.properties:Properties

# Groq API Configuration
spring.ai.openai.api-key=${GROQ_API_KEY}
spring.ai.openai.base-url=https://api.groq.com/openai
spring.ai.openai.chat.options.model=llama-3.3-70b-versatile

# File Storage Configuration
# Files are stored in project_root/uploads/notes

🛣️ API Endpoints (AI Features)

Method         Endpoint                            Description
GET        /api/notes/{id}/summarize       Returns/Generates a 3-point summary.
POST       /api/notes/{id}/flashcardsm     Generates 5 flashcards from the note content.
POST       /api/notes/{id}/questions       Generates 3 MCQs with choices and explanations.


🔧 Database Schema Logic
The system follows a Triangle Relationship for data integrity:

-Topic acts as the parent category.
-Note acts as the source material.
-AI Entities (Flashcards/Questions) are linked to both the Topic (for general study) and the Note (for traceability).

🌟 Future Roadmap
-Vector Search (RAG): Implementing a Vector Database (like PGVector) to allow users to "chat" with their entire library of notes.
-Spaced Repetition: An algorithm to schedule flashcard reviews based on AI-assessed difficulty.
-Frontend Integration: A React/Next.js dashboard for interactive study sessions.
