package com.niu.mdownloader;

import android.content.Context;

/**
 * Created by sean on 2017/3/28
 */
public class DownLoadConfig {


    private int downSize = 1024*1024*4;

    private int queueSize = 16;

    private int corePoolSize = 8;

    private int maxPoolSize = 24;

    public int getDownSize() {
        return downSize;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public DownLoadConfig() {
    }

    public static class Builder{

        private DownLoadConfig downLoadConfig;
        public Builder() {
            downLoadConfig = new DownLoadConfig();
        }

        public void setDownSize(int downSize) {
            downLoadConfig.downSize = downSize;
        }

        public void setQueueSize(int queueSize) {
            downLoadConfig.queueSize = queueSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            downLoadConfig.corePoolSize = corePoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            downLoadConfig.maxPoolSize = maxPoolSize;
        }

        public DownLoadConfig build(){
            return downLoadConfig;
        }
    }

}
