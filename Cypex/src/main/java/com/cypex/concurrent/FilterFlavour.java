package com.cypex.concurrent;

import com.cypex.utils.Flavour;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class FilterFlavour {
    private List<Filter> candies = new CopyOnWriteArrayList<>();

    public FilterFlavour() {
       generateQueue();
    }

    public Filter getCandies(int flavour) {
        return candies.get(flavour);
    }

    private void generateQueue() {
        for (int index = 0; index < Flavour.size(); index++) {
            candies.add(new Filter());
        }
    }
}
