package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.model.Question;
import ru.otus.service.QuestionsCSVServiceImpl;
import ru.otus.service.QuestionsFileService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionsFileService service = context.getBean(QuestionsCSVServiceImpl.class);
        List<Question> questions = service.getQuestions();

        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            for (String option : question.getOptions()) {
                System.out.println(" - " + option);
            }
            System.out.println("Сorrect answer: " + question.getCorrectAnswer());
            System.out.println();
        }
    }
}