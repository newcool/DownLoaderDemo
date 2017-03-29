package com.niu.downloaderdemo;

import android.app.DownloadManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.niu.mdownloader.DLManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

//    private ProgressBar pb1,pb2,pb3,pb4,pb5,pb6;
//    private Button bt1_1,bt1_2,bt1_3,bt2_1,bt2_2,bt2_3,bt3_1,bt3_2,bt3_3,bt4_1,bt4_2,bt4_3,bt5_1,bt5_2,bt5_3,bt6_1,bt6_2,bt6_3;
    private ListView listView;
    private MyListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        setContentView(R.layout.activity_main);

        DLManager.getDlManager(this).init();
        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new MyListAdapter(this);
        listView.setAdapter(listAdapter);

      /*  pb1 = (ProgressBar) findViewById(R.id.pb1);
        pb2 = (ProgressBar) findViewById(R.id.pb2);
        pb3 = (ProgressBar) findViewById(R.id.pb3);
        pb4 = (ProgressBar) findViewById(R.id.pb4);
        pb5 = (ProgressBar) findViewById(R.id.pb5);
        pb6 = (ProgressBar) findViewById(R.id.pb6);

        bt1_1 = (Button) findViewById(R.id.bt1_1);
        bt1_2 = (Button) findViewById(R.id.bt1_2);
        bt1_3 = (Button) findViewById(R.id.bt1_3);*/
    }


    @Override
    public void onClick(View v) {
    }
}
