package com.cypex.api.impl;

import com.cypex.api.ICandy;
import com.cypex.utils.Flavour;

import java.util.Random;

public class ICandyImpl implements ICandy {
    private Flavour flavour;
    public ICandyImpl() {
        flavour = Flavour.values()[new Random().nextInt(Flavour.size() - 1)];
    }
    public int getCandyFlavour() {
        return flavour.getId();
    }

    @Override
    public String toString() {
        return "конфета со вкусом " + flavour.name();
    }
}
