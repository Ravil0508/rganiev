package ru.otus.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.ProcessQuestionService;

@ShellComponent
public class ShellTestManagement {

    private final ProcessQuestionService service;

    public ShellTestManagement(ProcessQuestionService service) {
        this.service = service;
    }

    @ShellMethod(key = "start", value = "Стартует тест.")
    public void start() {
        this.service.runTest();
    }

    @ShellMethod(key = "close", value = "Заканчивает работу программы.")
    public void close() {
        System.out.println("Программа завершена.");
        System.exit(0);
    }
}
