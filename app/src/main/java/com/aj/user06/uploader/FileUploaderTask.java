package com.aj.user06.uploader;

import android.os.Message;
import android.util.Log;

import com.aj.user06.threadpoolexecutoraj.Util;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;

/**
 * Created by Anjan Debnath on 9/25/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class FileUploaderTask implements Callable {

    private int fileNumber;
    private Message message;

    public FileUploaderTask(){
    }

    public void setFileNumber(int fileNum){
        this.fileNumber = fileNum;
    }

    @Override
    public Object call() throws Exception {

        fileUploadDemoTask();

        return null;
    }

    private Message fileUploadDemoTask(){

        try {
            // check if thread is interrupted before lengthy operation
            if (Thread.interrupted()) throw new InterruptedException();

            // In real world project, you might do some blocking IO operation
            // In this example, I just let the thread sleep for 3 second
            Thread.sleep(3000);

            // After work is finished, send a message to CustomThreadPoolManager
            message = Util.createMessage(Util.MESSAGE_ID, "File Upload " +
                    fileNumber + " completed");

            Log.e("AJ::", "M:"+message.getData());


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return message;
    }

}
