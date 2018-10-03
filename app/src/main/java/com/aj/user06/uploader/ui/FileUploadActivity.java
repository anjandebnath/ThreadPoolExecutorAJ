package com.aj.user06.uploader.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.aj.user06.threadpoolexecutoraj.R;
import com.aj.user06.uploader.FileUploadManager;

/**
 * Created by Anjan Debnath on 10/2/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class FileUploadActivity extends AppCompatActivity {

    private TextView mDisplayTextView;
    private Button uploadButton;
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
        uploadButton = findViewById(R.id.upload);
        //Create view model if constructor is empty
        //ViewModelProviders.of(this).get(FileUploadViewModel.class);


        //Create view model if constructor is not empty
        // the factory and its dependencies instead should be injected with DI framework like Dagger
        FileUploadFactory factory =
                new FileUploadFactory(FileUploadManager.getsInstance());

        fileUploadViewModel = ViewModelProviders.of(this, factory).get(FileUploadViewModel.class);

        uploadButton.setOnClickListener(
                v -> fileUploadViewModel.initializeFileUpload());
                //fileUploadViewModel.initializeFileUploadWithRX());

    }


}
