package com.niu.mdownloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络操作类
 * Created by sean on 2017/2/14
 */
public class NetUtil {
    private String TAG = "NetUtil";

    public static HttpURLConnection getConnection(String url){
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setUseCaches(false);
            connection.setRequestProperty("Accept-Language", "zh-CN");
            connection.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept-Ranges", "bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
