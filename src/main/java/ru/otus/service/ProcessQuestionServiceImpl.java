package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.model.Question;

import java.util.List;

@Service
public class ProcessQuestionServiceImpl implements ProcessQuestionService{

    private QuestionsFileService fileService;

    private PrintService printService;

    private int passingGrade;

    public ProcessQuestionServiceImpl(QuestionsFileService fileService, PrintService printService, @Value("${passing.grade}") int passingGrade) {
        this.fileService = fileService;
        this.printService = printService;
        this.passingGrade = passingGrade;
    }

    public void runTest() {
        List<Question> questions = fileService.getQuestions();
        String fio = "";
        printService.render("Enter your first and last name");
        fio = printService.readLine();
        printService.render("Hello " + fio + "! Answer the following questions (To select an answer, enter the word corresponding to the option):");
        int trueAnswerCount = 0;

        for (Question question : questions) {
            printService.render(question.getQuestionText());
            int count = 1;
            for (String option : question.getOptions()) {
                printService.render(count + " - " + option);
                count++;
            }
            String ourAnswer = printService.readLine();
            trueAnswerCount = ourAnswer.equals(question.getCorrectAnswer()) ? trueAnswerCount + 1 : trueAnswerCount;
        }
        if(trueAnswerCount >= passingGrade)
            printService.render("Тест пройден! Количество правильных ответов: " + trueAnswerCount);
        else
            printService.render("Тест не пройден! Количество правильных ответов: " + trueAnswerCount);
    }
}
