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
@DisplayName("Тесты сервиса ProcessQuestionService")
class ProcessQuestionServiceTest {

    @Mock
    private QuestionsFileService questionsFileService;

    private List<Question> testQuestions;

    @BeforeEach
    void setUp() {
        testQuestions = List.of(
            new Question(
                "Какая столица Франции?",
                List.of("Лондон", "Париж", "Берлин", "Мадрид"),
                "Париж"
            ),
            new Question(
                "Сколько дней в високосном году?",
                List.of("365", "366", "364", "367"),
                "366"
            ),
            new Question(
                "Какой химический элемент обозначается символом O?",
                List.of("Золото", "Кислород", "Водород", "Углерод"),
                "Кислород"
            )
        );
    }

    @Test
    @DisplayName("Должен загрузить вопросы из файлового сервиса")
    void shouldLoadQuestionsFromFileService() {
        when(questionsFileService.getQuestions()).thenReturn(testQuestions);

        List<Question> questions = questionsFileService.getQuestions();

        assertThat(questions)
            .isNotNull()
            .hasSize(3)
            .containsExactlyElementsOf(testQuestions);
        verify(questionsFileService).getQuestions();
    }

    @Test
    @DisplayName("Должен корректно обработать пустой список вопросов")
    void shouldHandleEmptyQuestionsList() {
        when(questionsFileService.getQuestions()).thenReturn(List.of());

        List<Question> questions = questionsFileService.getQuestions();

        assertThat(questions).isEmpty();
        verify(questionsFileService).getQuestions();
    }

    @Test
    @DisplayName("Должен вернуть первый вопрос из списка")
    void shouldReturnFirstQuestion() {
        when(questionsFileService.getQuestions()).thenReturn(testQuestions);

        List<Question> questions = questionsFileService.getQuestions();
        Question firstQuestion = questions.get(0);

        assertThat(firstQuestion.getQuestionText())
            .isEqualTo("Какая столица Франции?");
        assertThat(firstQuestion.getCorrectAnswer()).isEqualTo("Париж");
    }

    @Test
    @DisplayName("Должен проверить что все вопросы имеют правильные ответы")
    void shouldVerifyAllQuestionsHaveCorrectAnswers() {
        when(questionsFileService.getQuestions()).thenReturn(testQuestions);

        List<Question> questions = questionsFileService.getQuestions();

        assertThat(questions).allSatisfy(question -> {
            assertThat(question.getCorrectAnswer()).isNotBlank();
            assertThat(question.getOptions()).contains(question.getCorrectAnswer());
        });
    }

    @Test
    @DisplayName("Должен вызвать сервис только один раз при получении вопросов")
    void shouldCallServiceOnlyOnce() {
        when(questionsFileService.getQuestions()).thenReturn(testQuestions);

        questionsFileService.getQuestions();

        verify(questionsFileService, times(1)).getQuestions();
        verifyNoMoreInteractions(questionsFileService);
    }
}
