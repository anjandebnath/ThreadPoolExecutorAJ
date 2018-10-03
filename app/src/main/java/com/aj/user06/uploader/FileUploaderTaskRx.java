package com.aj.user06.uploader;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.aj.user06.threadpoolexecutoraj.Util;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;

/**
 * Created by Anjan Debnath on 9/25/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class FileUploaderTaskRx implements  DataSource {

    private int fileNumber;
    private Message message;

    public FileUploaderTaskRx(){
    }

    public void setFileNumber(int fileNum){
        this.fileNumber = fileNum;
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

    /**
     * When we want to use RX java then we don't need to initialize
     * callable thread.
     * @return
     */
    @Override
    public Flowable<Message> getMessages() {

        return Flowable.fromCallable(new Callable<Message>() {
            @Override
            public Message call() throws Exception {
                return fileUploadDemoTask();
            }
        });


    }
}
