package ru.otus.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class PrintServiceImplementation implements PrintService {

    private final Scanner scanner;

    public PrintServiceImplementation() {
        this.scanner = new Scanner(System.in);
    }

    public void render(String message) {
        System.out.println(message);
    }

    public String readLine() {
        return scanner.nextLine();
    }
}
