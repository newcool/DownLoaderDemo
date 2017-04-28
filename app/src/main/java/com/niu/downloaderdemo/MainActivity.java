package com.niu.downloaderdemo;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.niu.mdownloader.DLManager;
import com.niu.mdownloader.DownLoadConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    private MyListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        setContentView(R.layout.activity_main);

        DownLoadConfig config = new DownLoadConfig();
        DLManager.getDlManager(this,config).init();
        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new MyListAdapter(this);
        listView.setAdapter(listAdapter);
    }


    @Override
    public void onClick(View v) {
    }
}
