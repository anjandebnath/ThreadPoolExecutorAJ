package com.aj.user06.uploader.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Message;
import android.util.Log;

import com.aj.user06.uploader.FileUploadManager;
import com.aj.user06.uploader.FileUploaderTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anjan Debnath on 10/2/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class FileUploadViewModel extends ViewModel {

    private FileUploadManager fileUploadManager;

    private MutableLiveData<Message> fileUploadStatus = new MutableLiveData<>();

    public FileUploadViewModel(FileUploadManager fileUploadManager){

        // get the thread pool manager instance
        this.fileUploadManager = fileUploadManager;

    }


    public void initializeFileUpload(int number){

        FileUploaderTask fileUploaderTask = new FileUploaderTask();
        fileUploaderTask.setFileNumber(number);

        fileUploadManager.addFileUploadTask(fileUploaderTask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::status, this::errorCode);

    }



    public MutableLiveData<Message> getFileUploadStatus() {
        return fileUploadStatus;
    }


    public void status(Message message){
        //Log.e("AJ::", "M:"+message.getData());
        fileUploadStatus.setValue(message);
    }

    public void errorCode(Throwable e){
        Log.d("AddTask", "e"+ e.toString());
    }

}
