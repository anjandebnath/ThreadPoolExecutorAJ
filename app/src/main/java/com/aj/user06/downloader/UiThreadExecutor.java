package com.aj.user06.downloader;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by Anjan Debnath on 9/20/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */

//UiThreadExecutor uses handler of the main thread to run tasks on the main thread.
public class UiThreadExecutor implements Executor {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {
        handler.post(command);
    }
}
