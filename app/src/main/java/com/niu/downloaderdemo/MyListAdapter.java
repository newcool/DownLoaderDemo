package com.niu.downloaderdemo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import com.niu.mdownloader.Config;
import com.niu.mdownloader.DLManager;
import com.niu.mdownloader.OnDownLoadListerner;

import java.util.List;

/**
 * Created by sean on 2017/2/15
 */
public class MyListAdapter extends BaseAdapter{
    private String TAG = "MyListAdapter";

    private Context context;
    public MyListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(context,R.layout.download_item,null);
            holder = new ViewHolder();
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.pb1);
            holder.bt1_1 = (Button) convertView.findViewById(R.id.bt1_1);
            holder.bt1_2 = (Button) convertView.findViewById(R.id.bt1_2);
            holder.bt1_3 = (Button) convertView.findViewById(R.id.bt1_3);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bt1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = new StringBuilder(Config.BASE_URL).append("/test").append(String.valueOf(position+1)).append(".zip").toString();
                DLManager.startDownLoad(url, Config.FILE_PATH, "test" + (position + 1) + ".zip", new OnDownLoadListerner() {
                    @Override
                    public void onProgress(int progress) {
                        Log.i(TAG, "onProgress: " + progress);
                        holder.progressBar.setProgress(progress);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });

        holder.bt1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.bt1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    class ViewHolder{
        private ProgressBar progressBar;
        private Button bt1_1,bt1_2,bt1_3;


    }
}
