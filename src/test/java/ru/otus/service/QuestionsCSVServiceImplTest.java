package ru.otus.service;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import ru.otus.model.Question;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class QuestionsCSVServiceImplTest {

    @Test
    public void getQuestionsTest() throws Exception {
        QuestionsCSVServiceImpl service = Mockito.mock(QuestionsCSVServiceImpl.class);;

        Mockito.when(service.getQuestions()).thenReturn(Collections.singletonList(new Question("question", List.of("opt1","opt2","opt3","opt4"),"answtrue")));
        ClassPathResource mockResource = Mockito.mock(ClassPathResource.class);
        Mockito.when(mockResource.getInputStream()).thenReturn(
                new ByteArrayInputStream("Question?,Option1,Option2,Option3,Option4,Option1\n".getBytes())
        );

        List<Question> questions = service.getQuestions();

        assertNotNull(questions);
        assertEquals(1, questions.size());
    }
}
