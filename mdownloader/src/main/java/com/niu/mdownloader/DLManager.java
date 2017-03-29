package com.niu.mdownloader;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by sean on 2017/2/13
 */
public class DLManager {
    private String TAG = "DLManager";

    private static DLManager dlManager;

    private static ThreadPoolExecutor mThreadPool;
    private static Context context;
    private static Handler messageHandler;

    private static ConcurrentHashMap<String, DLThread> downloadingMap;
    private static ConcurrentHashMap<String, RunnableFuture> futureMap;
    private static ConcurrentHashMap<String, OnDownLoadListerner> listernerMap;

    private DownLoadConfig downLoadConfig;

    private DLManager(Context context,DownLoadConfig downLoadConfig){
        this.context = context;
        this.downLoadConfig = downLoadConfig;
    }

    public static DLManager getDlManager(Context context,DownLoadConfig downLoadConfig){
        if(dlManager == null){
            synchronized (DLManager.class){
                if(dlManager == null){
                    dlManager = new DLManager(context,downLoadConfig);
                }
            }
        }
        return dlManager;
    }


    public DLManager init(){
        downloadingMap = new ConcurrentHashMap<>();
        futureMap = new ConcurrentHashMap<>();
        listernerMap = new ConcurrentHashMap<>();
        createHandler();
        mThreadPool = new ThreadPoolExecutor(downLoadConfig.getCorePoolSize(),downLoadConfig.getMaxPoolSize(),1, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(downLoadConfig.getQueueSize()),new CustomThreadFactory(),new CustomHandler());
        return  this;
    }

    public static void startDownLoad(String url,String path,String filename,OnDownLoadListerner onDownLoadListerner){

            if(!downloadingMap.containsKey(url)){
                DLThread dlThread = new DLThread(context,url,path,filename,messageHandler);
                RunnableFuture runnableFuture = (RunnableFuture) mThreadPool.submit(dlThread);
                futureMap.put(url,runnableFuture);
                downloadingMap.put(url,dlThread);
                listernerMap.put(url,onDownLoadListerner);
            }else {

            }
    }

    public static void stopDownLoad(String url){

    }


    private void createHandler() {
        messageHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                OnDownLoadListerner listerner = null;
                if (msg.obj instanceof DownLoadMessage.ProgressMessage) {
                    DownLoadMessage.ProgressMessage progressMessage = (DownLoadMessage.ProgressMessage) msg.obj;
                    listerner = listernerMap.get(progressMessage.url);
                    if(listerner != null){
                        listerner.onProgress(progressMessage.progress);
                    }
                }else if(msg.obj instanceof DownLoadMessage.CompleteMessage){

                }else if(msg.obj instanceof DownLoadMessage.ErrorMessage){

                }else if(msg.obj instanceof DownLoadMessage.StartMessage){

                }
            }
        };
    }
}
