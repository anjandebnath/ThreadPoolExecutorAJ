package com.aj.user06.downloader.ui;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aj.user06.downloader.DownloadManager;
import com.aj.user06.downloader.DownloadResultUpdateTask;
import com.aj.user06.downloader.DownloadTask;
import com.aj.user06.threadpoolexecutoraj.R;

import java.util.List;

/**
 * Created by Anjan Debnath on 9/24/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class FilesRecyclerViewAdapter extends
        RecyclerView.Adapter<FilesRecyclerViewAdapter.ViewHolder> {

    private List<String> fileList;
    private Context context;

    private String DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_DOWNLOADS).getPath();

    public FilesRecyclerViewAdapter(List<String> list, Context ctx) {
        fileList = list;
        context = ctx;
    }
    @Override
    public int getItemCount() {
        return fileList.size();
    }

    @Override
    public FilesRecyclerViewAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_file, parent, false);

        FilesRecyclerViewAdapter.ViewHolder viewHolder =
                new FilesRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FilesRecyclerViewAdapter.ViewHolder holder, int position) {
        final int itemPos = position;
        final String fileName = fileList.get(position);
        holder.fileName.setText(fileName);

        final TextView downloadStatus = holder.downloadStatus;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(fileName, downloadStatus);
            }
        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fileName;
        public TextView downloadStatus;

        public ViewHolder(View view) {
            super(view);
            fileName = view.findViewById(R.id.file_name_i);
            downloadStatus = view.findViewById(R.id.download_file_status);
        }

    }
    private String getFileName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }
    private void downloadFile(String url, TextView downloadStatus){
        String localFile = DOWNLOAD_DIR+"/"+getFileName(url);
        Log.e("DownloadManagerName", localFile);
        DownloadResultUpdateTask drUpdateTask = new DownloadResultUpdateTask(downloadStatus);

        DownloadTask downloadTask = new DownloadTask(url, localFile, drUpdateTask);
        DownloadManager.getDownloadManager().runDownloadFile(downloadTask);

    }
}
