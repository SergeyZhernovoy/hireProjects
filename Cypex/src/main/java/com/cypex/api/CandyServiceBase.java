package com.cypex.api;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Сервис пожирания конфет, требует реализации
 */
public abstract class CandyServiceBase {

    protected final BlockingQueue<ICandyEater> candyEaters;
    protected final ThreadPoolExecutor executor;

    /**
     * Сервис получает при инициализации массив доступных пожирателей конфет
     * @param candyEaters
     */
    public CandyServiceBase(ICandyEater[] candyEaters) {
        this.candyEaters = new ArrayBlockingQueue<>(candyEaters.length);
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        setCandyEaters(candyEaters);
    }

    private void setCandyEaters(ICandyEater[] candyEaters) {
        this.candyEaters.addAll(Arrays.asList(candyEaters));
    }

    public void shutdown() {
        executor.shutdown();
    }

    /**
     * Добавить конфету на съедение
     * @param candy
     */
    public abstract void addCandy(ICandy candy);
}