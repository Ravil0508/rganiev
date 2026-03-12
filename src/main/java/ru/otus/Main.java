package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.model.Question;
import ru.otus.service.ProcessQuestionService;
import ru.otus.service.QuestionsCSVServiceImpl;
import ru.otus.service.QuestionsFileService;
import java.util.List;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        ProcessQuestionService service = context.getBean(ProcessQuestionService.class);
        service.runTest();
    }
}