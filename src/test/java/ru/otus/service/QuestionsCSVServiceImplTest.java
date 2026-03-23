package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты сервиса QuestionsCSVServiceImpl")
class QuestionsCSVServiceImplTest {

    private QuestionsCSVServiceImpl service;
    private QuestionsCSVServiceImpl emptyService;
    private QuestionsCSVServiceImpl invalidService;

    @BeforeEach
    void setUp() {
        service = new QuestionsCSVServiceImpl("test-questions.csv");
        emptyService = new QuestionsCSVServiceImpl("empty-questions.csv");
        invalidService = new QuestionsCSVServiceImpl("non-existent-file.csv");
    }

    @Test
    @DisplayName("Должен успешно загрузить вопросы из CSV файла")
    void shouldLoadQuestionsFromCsvFile() {
        List<Question> questions = service.getQuestions();

        assertThat(questions)
            .isNotNull()
            .isNotEmpty()
            .hasSizeGreaterThanOrEqualTo(5);
    }

    @Test
    @DisplayName("Должен корректно парсить первый вопрос из файла")
    void shouldParseFirstQuestionCorrectly() {
        List<Question> questions = service.getQuestions();
        Question firstQuestion = questions.get(0);

        assertThat(firstQuestion.getQuestionText())
            .isEqualTo("Как называется столица Германии?");
        assertThat(firstQuestion.getOptions())
            .hasSize(4)
            .containsExactly(
                "Берлин",
                "Мюнхен",
                "Франкфурт",
                "Гамбург"
            );
        assertThat(firstQuestion.getCorrectAnswer())
            .isEqualTo("Берлин");
    }

    @Test
    @DisplayName("Должен корректно парсить все вопросы с правильными ответами")
    void shouldParseAllQuestionsWithCorrectAnswers() {
        List<Question> questions = service.getQuestions();

        assertThat(questions).allSatisfy(question -> {
            assertThat(question.getQuestionText()).isNotBlank();
            assertThat(question.getOptions()).hasSize(4);
            assertThat(question.getCorrectAnswer()).isNotBlank();
        });
    }

    @Test
    @DisplayName("Должен вернуть пустой список при чтении пустого CSV файла")
    void shouldReturnEmptyListForEmptyCsvFile() {
        List<Question> questions = emptyService.getQuestions();

        assertThat(questions)
            .isNotNull()
            .isEmpty();
    }

    @Test
    @DisplayName("Должен вернуть пустой список при ошибке чтения файла")
    void shouldReturnEmptyListOnFileReadError() {
        List<Question> questions = invalidService.getQuestions();

        assertThat(questions)
            .isNotNull()
            .isEmpty();
    }

    @Test
    @DisplayName("Должен корректно обработать вопрос про океан")
    void shouldHandleOceanQuestion() {
        List<Question> questions = service.getQuestions();
        Question oceanQuestion = questions.stream()
            .filter(q -> q.getQuestionText().toLowerCase().contains("океан"))
            .findFirst()
            .orElse(null);

        assertThat(oceanQuestion).isNotNull();
        assertThat(oceanQuestion.getCorrectAnswer()).isEqualTo("Тихий");
    }

    @Test
    @DisplayName("Должен пропустить заголовок CSV файла")
    void shouldSkipCsvHeader() {
        List<Question> questions = service.getQuestions();

        assertThat(questions)
            .noneMatch(q -> q.getQuestionText().contains("questionText"));
    }

    @Test
    @DisplayName("Должен создать сервис с корректным путем к файлу")
    void shouldCreateServiceWithCorrectPath() {
        String path = "custom-questions.csv";

        QuestionsCSVServiceImpl customService = new QuestionsCSVServiceImpl(path);

        assertThat(customService).isNotNull();
    }

    @Test
    @DisplayName("Должен вернуть вопросы с уникальными текстами")
    void shouldReturnQuestionsWithUniqueTexts() {
        List<Question> questions = service.getQuestions();
        long uniqueCount = questions.stream()
            .map(Question::getQuestionText)
            .distinct()
            .count();

        assertThat(uniqueCount).isEqualTo(questions.size());
    }
}
