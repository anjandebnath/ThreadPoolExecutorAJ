package com.aj.user06.uploader;

import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aj.user06.threadpoolexecutoraj.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by Anjan Debnath on 10/1/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class FileUploadManager {


    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT;

    private static FileUploadManager sInstance = null;


    //an interface which extends Executor interface. It is used to manage threads in the threads pool.
    private final ExecutorService mExecutorService;

    private final BlockingQueue<Runnable> mTaskQueue;


    // The class is used as a singleton
    static {
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        sInstance = new FileUploadManager();
    }

    // Made constructor private to avoid the class being initiated from outside
    private FileUploadManager(){

        // initialize a queue for the thread pool. New tasks will be added to this queue
        mTaskQueue = new LinkedBlockingQueue<Runnable>();
        mExecutorService = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES*2,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mTaskQueue,
                new FileUploadManager.BackgroundThreadFactory());
    }

    private static class BackgroundThreadFactory implements ThreadFactory {

        private static int sTag = 1;

        @Override
        public Thread newThread(@NonNull Runnable runnable) {

            Thread thread = new Thread(runnable);
            thread.setName("CustomThread" + sTag);
            thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);

            // A exception handler is created to log the exception from threads
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    Log.e("TPE", thread.getName() + " encountered an error: " + ex.getMessage());
                }
            });
            return thread;
        }
    }

    public static FileUploadManager getsInstance(){
        return sInstance;
    }

    // Add a callable to the queue, which will be executed by the next available thread in the pool
    public Flowable<Message> addFileUploadTask(FileUploaderTask fileUploaderTask){

        Future<Message> future = mExecutorService.submit(fileUploaderTask);

        try {
            Message message = future.get();

            /*Flowable<Message> messageFlowable = Flowable.create(subscriber -> {
                while (!subscriber.isCancelled()) {
                    subscriber.onNext(message);
                }
            }, BackpressureStrategy.DROP);*/


            return Flowable.fromCallable(() -> message);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

      return null;
    }


}
