package com.niu.mdownloader;

import android.util.Log;

import java.io.IOException;

/**
 * 文件操作类
 * Created by sean on 2017/3/6
 */
public class FileUtil {
    private String TAG = "FileUtil";

    //改变文件权限
    public static void changeChmod(String file) {
        int status3;
        try {
            Process p3 = Runtime.getRuntime().exec("chmod 777 " + file);
            status3 = p3.waitFor();
            if (status3 == 0) {
                Log.e("FileUtil", "chmod succeed 文件夹= " + file);
            } else {
                Log.e("FileUtil", "chmod failed 文件夹 = " + file);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
