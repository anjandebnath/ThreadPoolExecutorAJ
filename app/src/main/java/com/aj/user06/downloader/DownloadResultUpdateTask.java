package com.aj.user06.downloader;

import android.widget.TextView;

/**
 * Created by Anjan Debnath on 9/24/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class DownloadResultUpdateTask implements Runnable{

    private TextView message;
    private String backgroundMsg;

    public DownloadResultUpdateTask(TextView msg){
        message = msg;
    }
    public void setBackgroundMsg(String bmsg){
        backgroundMsg = bmsg;
    }
    @Override
    public void run() {
        message.setText(backgroundMsg);
    }
}
