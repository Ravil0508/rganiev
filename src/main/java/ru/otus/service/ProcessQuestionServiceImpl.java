package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.conf.LocalConfiguration;
import ru.otus.model.Question;

import java.util.List;
import java.util.Locale;

@Service
public class ProcessQuestionServiceImpl implements ProcessQuestionService{

    private QuestionsFileService fileService;

    private PrintService printService;

    private MessageSource messageSource;

    private Locale locale;

    private int passingGrade;

    public ProcessQuestionServiceImpl(QuestionsFileService fileService, PrintService printService, @Value("${passing.grade}") int passingGrade, MessageSource messageSource, LocalConfiguration localConfiguration) {
        this.fileService = fileService;
        this.printService = printService;
        this.passingGrade = passingGrade;
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localConfiguration.getLocale());
    }

    public void runTest() {
        List<Question> questions = fileService.getQuestions();
        String fio = "";
        printService.render(messageSource.getMessage("test.enter.name", null, locale));
        fio = printService.readLine();
        printService.render(messageSource.getMessage("test.greeting", new String[] {fio}, locale));
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
            printService.render(messageSource.getMessage("test.passed", new String[] {String.valueOf(trueAnswerCount)}, locale));
        else
            printService.render(messageSource.getMessage("test.failed", new String[] {String.valueOf(trueAnswerCount)}, locale));
    }
}
