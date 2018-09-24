package com.aj.user06.downloader.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aj.user06.threadpoolexecutoraj.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anjan Debnath on 9/24/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class DownloadFilesActivity extends AppCompatActivity {

    private static final String TAG = "DownloadFilesActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_files);

        RecyclerView fileRv = findViewById(R.id.files_rv);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        fileRv.setLayoutManager(recyclerLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                fileRv.getContext(),recyclerLayoutManager.getOrientation());
        fileRv.addItemDecoration(dividerItemDecoration);

        FilesRecyclerViewAdapter recyclerViewAdapter = new FilesRecyclerViewAdapter(
                getFilesList(), this);
        fileRv.setAdapter(recyclerViewAdapter);

        //storage write permission needed to save downloaded file on device
        writeStoragePermission();
    }
    private void writeStoragePermission(){
        if (ContextCompat.checkSelfPermission(DownloadFilesActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadFilesActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

        }
    }
    private List<String> getFilesList(){
        List<String> files = new ArrayList<String>();
        files.add("https://s3.us-east-2.amazonaws.com/azim-file-upload/Anjan-1.png");
        files.add("https://s3.us-east-2.amazonaws.com/azim-file-upload/Anjan-2.jpeg");
        files.add("https://s3.us-east-2.amazonaws.com/azim-file-upload/Anjan-3.jpg");
        return files;
    }
}
