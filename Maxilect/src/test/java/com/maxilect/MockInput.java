package com.maxilect;

import com.maxilect.api.Input;

public class MockInput implements Input {

    private int index;
    private String[] answers = {"p","Sergey","topot","p","Alex","topot","p","Sergey","ropor","q"};

    @Override
    public String ask(String message) {
        System.out.println("mock say :" + answers[index]);
        return answers[index++];
    }
}
