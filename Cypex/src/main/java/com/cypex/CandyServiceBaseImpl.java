package com.cypex;

import com.cypex.api.ICandy;
import com.cypex.api.*;
import com.cypex.api.impl.*;
import com.cypex.concurrent.*;

import java.util.concurrent.BlockingQueue;

public class CandyServiceBaseImpl extends CandyServiceBase {
    private final FilterFlavour filterFlavour;

    /**
     * Сервис получает при инициализации массив доступных пожирателей конфет
     *
     * @param candyEaters
     */
    public CandyServiceBaseImpl(ICandyEater[] candyEaters) {
        super(candyEaters);
        filterFlavour = new FilterFlavour();
    }

    public BlockingQueue<ICandyEater> getEaters() {
        return this.candyEaters;
    }

    public void addCandy(final ICandy candy) {
        int flavour = candy.getCandyFlavour();
        Filter filter = filterFlavour.getCandies(flavour);
        BlockingQueue<ICandy> candies = filter.getBlockingQueue();
        candies.add(candy);
        Task task = new Task(getEaters(), candies, filter);
        this.executor.execute(task);
    }

    public static void main(String[] args) {
        ICandyEater[] candyEaters = {
                new ICandyEaterImpl("первый"),
                new ICandyEaterImpl("второй"),
                new ICandyEaterImpl("третий"),
                new ICandyEaterImpl("четвертый")
        };
        CandyServiceBase candyServiceBase = new CandyServiceBaseImpl(candyEaters);
        for (int index = 0; index < 25; index++) {
            ICandy candy = new ICandyImpl();
            System.out.println("Поступила " + candy.toString() + " hash " + candy.hashCode());
            candyServiceBase.addCandy(candy);
        }
        candyServiceBase.shutdown();
    }
}
