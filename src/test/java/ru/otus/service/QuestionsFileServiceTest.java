package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.model.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты интерфейса QuestionsFileService")
class QuestionsFileServiceTest {

    @Mock
    private QuestionsFileService questionsFileService;

    private List<Question> testQuestions;

    @BeforeEach
    void setUp() {
        testQuestions = List.of(
            new Question(
                "Какая столица Италии?",
                List.of("Рим", "Милан", "Венеция", "Флоренция"),
                "Рим"
            ),
            new Question(
                "В каком году открыли Америку?",
                List.of("1492", "1500", "1488", "1510"),
                "1492"
            )
        );
    }

    @Test
    @DisplayName("Должен вернуть список вопросов через мок")
    void shouldReturnQuestionsListThroughMock() {
        when(questionsFileService.getQuestions()).thenReturn(testQuestions);

        List<Question> questions = questionsFileService.getQuestions();

        assertThat(questions)
            .isNotNull()
            .hasSize(2)
            .containsExactlyElementsOf(testQuestions);
        verify(questionsFileService).getQuestions();
    }

    @Test
    @DisplayName("Должен вызвать метод getQuestions один раз")
    void shouldCallGetQuestionsOnce() {
        when(questionsFileService.getQuestions()).thenReturn(testQuestions);

        questionsFileService.getQuestions();

        verify(questionsFileService, times(1)).getQuestions();
    }

    @Test
    @DisplayName("Должен вернуть пустой список если нет вопросов")
    void shouldReturnEmptyListWhenNoQuestions() {
        when(questionsFileService.getQuestions()).thenReturn(List.of());

        List<Question> questions = questionsFileService.getQuestions();

        assertThat(questions)
            .isNotNull()
            .isEmpty();
    }

    @Test
    @DisplayName("Должен корректно работать при множественных вызовах")
    void shouldWorkCorrectlyWithMultipleCalls() {
        when(questionsFileService.getQuestions()).thenReturn(testQuestions);

        List<Question> firstCall = questionsFileService.getQuestions();
        List<Question> secondCall = questionsFileService.getQuestions();

        assertThat(firstCall).isEqualTo(secondCall);
        verify(questionsFileService, times(2)).getQuestions();
    }
}
