package ru.otus.service;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.conf.LocalConfiguration;
import ru.otus.model.Question;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class QuestionsCSVServiceImpl implements QuestionsFileService {

    private String path;

    public QuestionsCSVServiceImpl(LocalConfiguration localConfiguration, @Value("${csvfile.base-name}") String baseName, @Value("${csvfile.suffix}") String suffix) {
        this.path = baseName + "_" + localConfiguration.getLocale() + "." + suffix;
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource(path).getInputStream()))) {
            String[] line;

            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String questionText = line[0];
                List<String> options = List.of(line[1], line[2], line[3], line[4]);
                String correctAnswer = line[5];

                Question question = new Question(questionText, options, correctAnswer);
                questions.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
}
