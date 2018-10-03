package com.aj.user06.uploader.ui;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.aj.user06.uploader.FileUploadManager;

/**
 * Created by Anjan Debnath on 10/2/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class FileUploadFactory implements ViewModelProvider.Factory {


    private FileUploadManager fileUploadManager;

    public FileUploadFactory(FileUploadManager fileUploadManager) {
        this.fileUploadManager = fileUploadManager;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FileUploadViewModel.class)) {
            return (T) new FileUploadViewModel(fileUploadManager);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
