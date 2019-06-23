package com.cypex.concurrent;

import com.cypex.api.ICandy;
import com.cypex.api.ICandyEater;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {
    private final BlockingQueue<ICandyEater> eaters;
    private final BlockingQueue<ICandy> candies;
    private final Filter filter;

    public Task(BlockingQueue<ICandyEater> eaters, BlockingQueue<ICandy> candies, Filter filter) {
        this.eaters = eaters;
        this.candies = candies;
        this.filter = filter;
    }

    @Override
    public void run() {
        try {
            Object lock = filter.getLock();
            synchronized (lock) {
                ICandyEater candyEater = eaters.take();
                ICandy candy = candies.take();
                candyEater.eat(candy);
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                eaters.put(candyEater);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
