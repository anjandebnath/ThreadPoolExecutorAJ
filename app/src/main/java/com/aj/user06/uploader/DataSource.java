package com.aj.user06.uploader;

import android.os.Message;

import io.reactivex.Flowable;

/**
 * Created by Anjan Debnath on 10/3/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public interface DataSource {

    Flowable<Message> getMessages();
}
