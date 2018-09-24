package com.aj.user06.executor_framework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Anjan Debnath on 9/18/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */


public class BasicThreadPool {

    static final int DEFAULT_THREAD_POOL_SIZE = 4;
    ExecutorService fixedExecutorService;



    public BasicThreadPool(){

        fixedExecutorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    }


    public void initializeCallable(){

        Callable<String> callable = new Callable<String>(){

            @Override
            public String call() throws Exception {
                // Perform some computation
                System.out.println("Entered Callable");
                Thread.sleep(2000);
                return "Hello from Callable";
            }
        };

        System.out.println("Submitting Callable");
        Future<String> future = fixedExecutorService.submit(callable);

        // This line executes immediately
        System.out.println("Do something else while callable is getting executed");

        System.out.println("Retrieve the result of the future");
        // Future.get() blocks until the result is available
        String result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(result);

        fixedExecutorService.shutdown();

    }






}
