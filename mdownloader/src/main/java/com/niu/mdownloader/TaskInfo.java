package com.niu.mdownloader;

/**
 * 下载任务实体类
 * Created by sean on 2017/2/14
 */
public class TaskInfo {

    private String TAG = "TaskInfo";

    public TaskInfo(String url, long size, long progress, String filepath) {
        this.url = url;
        this.size = size;
        this.progress = progress;
        this.filepath = filepath;
    }

    private String url;
    private long size;
    private long progress;
    private String filepath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
