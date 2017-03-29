package com.niu.mdownloader;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

/**
 * 下载实现类
 * Created by sean on 2017/2/13
 */
public class DLThread implements Runnable{
    private String TAG = "DLThread";
    private String url;
    InputStream inputStream;
    String filepath,filename;
    DBUtil dbUtil;
    RandomAccessFile randomFile;
    long progress;
    private Handler handler;
    long fileSize;
    private volatile boolean stop;

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public DLThread(Context context, String url, String filepath, String filename, Handler handler) {
        this.url = url;
        dbUtil = DBUtil.getInstance(context);
        this.filepath = filepath;
        this.filename = filename;
        this.handler = handler;
    }

    @Override
    public void run() {

        TaskInfo taskInfo = dbUtil.query(url);
        HttpURLConnection connection = NetUtil.getConnection(url);
        try {
            int responseCode = connection.getResponseCode();
            //重定向
            if(responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP){
                this.url = connection.getHeaderField("location");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //如果有下载记录，从下载记录处下载
        if(taskInfo != null){
            fileSize = taskInfo.getSize();
            File file = new File(filepath,filename);
            //如果文件存在
            if(file.exists()){
                //如果文件大小等于数据库中记录的大小则继续下载
                if(file.length() == fileSize){
                    connection.setRequestProperty("Range", "bytes=" + taskInfo.getProgress() + "-" + taskInfo.getSize());
                }
            }else {//如果文件不存在则更新数据库中下载进度置为0，重新下载
                taskInfo.setProgress(0);
            }

        }else {//新的下载任务
            fileSize = connection.getContentLength();
            if(fileSize == -1){
                sendError(new SocketTimeoutException());
                return;
            }else {
                taskInfo = new TaskInfo(url,fileSize,0,filepath);
                dbUtil.insert(taskInfo);
            }
        }

        progress = taskInfo.getProgress();

        File file = new File(filepath,filename);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileUtil.changeChmod(file.getAbsolutePath());
            randomFile = new RandomAccessFile(file,"rwd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte[] b = new byte[1024*4];
        int len;

        try {
            long lastTime = System.currentTimeMillis();
            while ((len = inputStream.read(b))!=-1){

                randomFile.write(b, 0, len);
                progress = progress+len;

                Log.i(TAG, "run: " + progress+":"+fileSize);
                //计算下载进度
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTime > 500) {
                    int percent = (int) (((float)progress/fileSize)*100);
                    lastTime = currentTime;
                    Message message = handler.obtainMessage();
                    message.obj = new DownLoadMessage.ProgressMessage(url,percent);
                    handler.sendMessage(message);
                }

                //暂停
                if(stop){
                    saveProgress(taskInfo);
                    closeResource(connection);
                    return;
                }
            }

            /**
             * 下载完成
             */
            if(file.length() == progress){
                Message message = handler.obtainMessage();
                message.obj = new DownLoadMessage.ProgressMessage(url,100);
                handler.sendMessage(message);
                Message complete = handler.obtainMessage();
                complete.obj = new DownLoadMessage.CompleteMessage(url);
                handler.sendMessage(complete);
                //删除数据库记录
                dbUtil.delete(url);
            }

        } catch (IOException e) {
            //下载过程中异常，保存进度
            saveProgress(taskInfo);
            sendError(e);
            e.printStackTrace();
        }

        closeResource(connection);
    }

    /**
     * 发送错误信息
     * @param e
     */
    private void sendError(IOException e) {
        Message error = handler.obtainMessage();
        error.obj = new DownLoadMessage.ErrorMessage(url,e);
        handler.sendMessage(error);
    }

    /**
     * 关闭资源
     * @param connection
     */
    private void closeResource(HttpURLConnection connection) {
        try {
            inputStream.close();
            randomFile.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(randomFile!=null){
                try {
                    randomFile.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存下载进度
     * @param taskInfo
     */
    private void saveProgress(TaskInfo taskInfo) {
        taskInfo.setProgress(progress);
        dbUtil.update(taskInfo);
    }

}
