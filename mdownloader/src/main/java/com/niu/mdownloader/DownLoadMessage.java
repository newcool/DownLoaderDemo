package com.niu.mdownloader;

import android.os.Message;

/**
 * 下载过程中的提示信息
 * Created by sean on 2017/3/1
 */
public class DownLoadMessage {
    private String TAG = "DownLoadMessage";

    public static class BaseMessage{
        public BaseMessage(String url) {
            this.url = url;
        }

        public String url;
    }

    public static class ProgressMessage extends BaseMessage{
        public int progress;

        public ProgressMessage(String url,int progress) {
            super(url);
            this.progress = progress;
        }
    }

    public static class CompleteMessage extends BaseMessage{
        public CompleteMessage(String url) {
            super(url);
        }
    }

    public static class StartMessage extends BaseMessage{

        public StartMessage(String url) {
            super(url);
        }
    }

    public static class ErrorMessage extends BaseMessage{
        public Exception exception;

        public ErrorMessage(String url,Exception exception) {
            super(url);
            this.exception = exception;
        }
    }

}
