package com.niu.mdownloader;

/**
 * Created by sean on 2017/2/13
 */

public interface OnDownLoadListerner {

    public void onProgress(int progress);

    public void onCompleted();

    public void onStart();

    public void onError();

}
