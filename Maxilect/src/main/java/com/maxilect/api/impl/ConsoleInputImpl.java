package com.maxilect.api.impl;

import com.maxilect.api.Input;

import java.util.Scanner;

public class ConsoleInputImpl implements Input {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String ask(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
