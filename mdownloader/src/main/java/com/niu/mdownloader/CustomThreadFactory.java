package com.niu.mdownloader;

import java.util.concurrent.ThreadFactory;

/**
 * Created by sean on 2017/3/28
 */
public class CustomThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
