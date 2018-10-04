package com.aj.user06.uploader.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.aj.user06.threadpoolexecutoraj.R;
import com.aj.user06.threadpoolexecutoraj.Util;
import com.aj.user06.uploader.FileUploadManager;

/**
 * Created by Anjan Debnath on 10/2/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class FileUploadActivity extends AppCompatActivity {

    private TextView mDisplayTextView;
    private Button uploadButton1;
    private Button uploadButton2;
    private Button uploadButton3;
    private Button uploadButton4;
    private FileUploadViewModel fileUploadViewModel;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        mDisplayTextView = (TextView)findViewById(R.id.display);
        uploadButton1 = findViewById(R.id.upload1);
        uploadButton2 = findViewById(R.id.upload2);
        uploadButton3 = findViewById(R.id.upload3);
        uploadButton4 = findViewById(R.id.upload4);

        mDisplayTextView.setText("Result::\n");
        //Create view model if constructor is empty
        //ViewModelProviders.of(this).get(FileUploadViewModel.class);


        //Create view model if constructor is not empty
        // the factory and its dependencies instead should be injected with DI framework like Dagger
        FileUploadFactory factory =
                new FileUploadFactory(FileUploadManager.getsInstance());

        fileUploadViewModel = ViewModelProviders.of(this, factory).get(FileUploadViewModel.class);

        uploadButton1.setOnClickListener(
                v -> fileUploadViewModel.initializeFileUpload(1));
                //fileUploadViewModel.initializeFileUploadWithRX());

        uploadButton2.setOnClickListener(
                v -> fileUploadViewModel.initializeFileUpload(2));

        uploadButton3.setOnClickListener(
                v -> fileUploadViewModel.initializeFileUpload(3));

        uploadButton4.setOnClickListener(
                v -> fileUploadViewModel.initializeFileUpload(4));

        fileUploadViewModel.getFileUploadStatus().observe(this, new Observer<Message>() {
            @Override
            public void onChanged(@Nullable Message message) {

                //Log.e("AJ::", "M:"+message.getData());

                if (message != null) {
                    mDisplayTextView.append(message.getData()+"\n");
                }
            }
        });

    }


}
