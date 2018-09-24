package com.aj.user06.downloader;

import android.os.Process;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aj.user06.threadpoolexecutoraj.UiThreadCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Anjan Debnath on 9/20/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */


//DownloadManager is a singleton class. It creates instance of
// 1. task queue,
// 2. ThreadPoolExecutor and
// 3. ui thread executor which is used to update UI with results.

public class DownloadManager {


    private static final TimeUnit KEEP_ALIVE_TIME_UNIT;
    private static DownloadManager downloadManager = null;

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 50;


    //The reference is later used to communicate with the UI thread
    private static UiThreadExecutor uiHandler;

    //an interface which extends Executor interface. It is used to manage threads in the threads pool.
    private final ThreadPoolExecutor downloadThreadPool;

    private final BlockingQueue<Runnable> mTaskQueue;
    private List<Future> mRunningTaskList;

    // The class is used as a singleton
    static {
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        downloadManager = new DownloadManager();
        uiHandler = new UiThreadExecutor();
    }

    private DownloadManager(){
        mTaskQueue = new LinkedBlockingQueue<Runnable>();
        mRunningTaskList = new ArrayList<>();
        downloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mTaskQueue,
                new BackgroundThreadFactory());
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

    public static DownloadManager getDownloadManager(){
        return downloadManager;
    }

    public void runDownloadFile(Runnable task){
        downloadThreadPool.execute(task);
    }

    //to runs task on main thread from background thread
    public UiThreadExecutor getUiThreadExecutor(){
        return uiHandler;
    }

}
