package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.model.Question;
import ru.otus.service.ProcessQuestionService;
import ru.otus.service.QuestionsCSVServiceImpl;
import ru.otus.service.QuestionsFileService;
import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        ProcessQuestionService service = context.getBean(ProcessQuestionService.class);
        service.runTest();
    }
}