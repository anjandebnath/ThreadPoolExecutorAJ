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

        Button uploadButton1 = findViewById(R.id.upload1);
        Button uploadButton2 = findViewById(R.id.upload2);
        Button uploadButton3 = findViewById(R.id.upload3);
        Button uploadButton4 = findViewById(R.id.upload4);

        mDisplayTextView.setText("Result::\n");
        //Create view model if constructor is empty
        //ViewModelProviders.of(this).get(FileUploadViewModel.class);


        //Create view model if constructor is not empty
        // the factory and its dependencies instead should be injected with DI framework like Dagger
        FileUploadFactory factory =
                new FileUploadFactory(FileUploadManager.getsInstance());


        //Create ViewModel
        fileUploadViewModel = ViewModelProviders.of(this, factory).get(FileUploadViewModel.class);

        uploadButton1.setOnClickListener(
                v -> fileUploadViewModel.initializeFileUpload(1, 9000));
                //fileUploadViewModel.initializeFileUploadWithRX());

        uploadButton2.setOnClickListener(
                v -> fileUploadViewModel.initializeFileUpload(2, 5000));

        uploadButton3.setOnClickListener(
                v -> fileUploadViewModel.initializeFileUpload(3, 2000));

        uploadButton4.setOnClickListener(
                v -> fileUploadViewModel.initializeFileUpload(4, 7000));


        // Mutable LiveData subscriber to get the latest data that will be used to update the UI
        fileUploadViewModel.getFileUploadStatus().observe(this, new Observer<Message>() {
            @Override
            public void onChanged(@Nullable Message message) {

                Log.e("AJ::", "M:"+message.getData());

                if (message != null) {
                    mDisplayTextView.append(message.getData()+"\n");
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        fileUploadViewModel.shutDownManager();
    }
}
