package com.cypex.concurrent;

import com.cypex.api.ICandy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Filter {
    private BlockingQueue<ICandy> blockingQueue = new ArrayBlockingQueue<>(16);
    private Object lock = new Object();

    public BlockingQueue<ICandy> getBlockingQueue() {
        return blockingQueue;
    }

    public Object getLock() {
        return lock;
    }

}
