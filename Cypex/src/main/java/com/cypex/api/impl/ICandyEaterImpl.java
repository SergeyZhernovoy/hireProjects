package com.cypex.api.impl;

import com.cypex.api.ICandy;
import com.cypex.api.ICandyEater;

import java.util.Random;

public class ICandyEaterImpl implements ICandyEater {
    private String name;
    public ICandyEaterImpl(String name) {
        this.name = name;
    }
    public void eat(ICandy candy) {
           System.out.println("Я " + name +  " скушал " + candy.toString() + " hash " + candy.hashCode() + " это было в " + System.currentTimeMillis());
    }
}
