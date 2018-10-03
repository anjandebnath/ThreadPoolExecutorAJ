package com.aj.user06.uploader.ui;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.aj.user06.threadpoolexecutoraj.Util;
import com.aj.user06.uploader.FileUploadManager;
import com.aj.user06.uploader.FileUploaderTask;
import com.aj.user06.uploader.FileUploaderTaskRx;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anjan Debnath on 10/2/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class FileUploadViewModel extends ViewModel {

    private FileUploadManager fileUploadManager;

    private static final int NUMBER = 4;

    public FileUploadViewModel(FileUploadManager fileUploadManager){

        // get the thread pool manager instance
        this.fileUploadManager = fileUploadManager;

    }


    public void initializeFileUpload(){

        FileUploaderTask fileUploaderTask = new FileUploaderTask();
        fileUploaderTask.setFileNumber(NUMBER);

        fileUploadManager.addFileUploadTask(fileUploaderTask);
    }

    public void initializeFileUploadWithRX(){

        FileUploaderTaskRx fileUploaderTaskRx = new FileUploaderTaskRx();
        fileUploaderTaskRx.setFileNumber(NUMBER);


        fileUploaderTaskRx.getMessages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::status, this::errorCode);

    }

    public void status(Message message){
        Bundle bundle = message.getData();
        String messsageText = bundle.getString(Util.MESSAGE_BODY, Util.EMPTY_MESSAGE);
        Log.e("AJ::", "M:"+message.getData());
    }

    public void errorCode(Throwable e){
        Log.d("AddTask", "e"+ e.toString());
    }



}
