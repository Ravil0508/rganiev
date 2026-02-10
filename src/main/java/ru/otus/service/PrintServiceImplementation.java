package ru.otus.service;

import org.springframework.stereotype.Service;

@Service
public class PrintServiceImplementation implements PrintService {

    public void render(String message) {
        System.out.println(message);
    }
}
