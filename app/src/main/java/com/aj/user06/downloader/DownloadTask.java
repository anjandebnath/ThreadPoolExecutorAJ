package com.aj.user06.downloader;

import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Anjan Debnath on 9/24/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class DownloadTask implements Runnable{
    String url;
    String localFile;
    DownloadResultUpdateTask resultUpdateTask;

    public DownloadTask(String urlIn, String localFileIn,
                        DownloadResultUpdateTask drUpdateTask){
        url = urlIn;
        localFile = localFileIn;
        resultUpdateTask = drUpdateTask;
    }

    @Override
    public void run() {
        String msg;
        if(downloadFile()){
            msg = "file downloaded successful "+url;
        }else{
            msg = "failed to download the file "+url;
        }
        //update results download status on the main thread
        resultUpdateTask.setBackgroundMsg(msg);
        DownloadManager.getDownloadManager().getUiThreadExecutor()
                .execute(resultUpdateTask);
    }

    public boolean downloadFile(){
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            Response response = client.newCall(request).execute();

            InputStream in = response.body().byteStream();
            FileOutputStream fileOutput =
                    new FileOutputStream(localFile);

            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = in.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
            }
            fileOutput.close();
            response.body().close();
        }catch (Exception e){e.printStackTrace(); return false;}
        return true;
    }
}
