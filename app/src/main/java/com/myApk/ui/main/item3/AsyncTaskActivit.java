package com.myApk.ui.main.item3;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myApk.R;

/**
 * Created by admin on 2015/9/5.
 */
public class AsyncTaskActivit extends Activity
{
    private ProgressBar progressBar;
    private TextView tv_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        progressBar = (ProgressBar) findViewById(R.id.pb_progressBar);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        Button btn = (Button) findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }
    //防止二次点击
    public  boolean isRunning =false;
    private void start()
    {
        if (isRunning){return;}

        new AsyncTask<Void,Integer,Void>()
        {
            int progress =0;
            //任务开始前
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
            }
            //回调方法:条件:onPreExecute()执行完之后 任务中
            // 子线程不能更新UI
            @Override
            protected Void doInBackground(Void... voids) {
                for (int i=0;i< 100;i++)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ++progress;
                    //发布进度
                    publishProgress(progress);
                }
                return null;
            }

            //进度发生变化
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                progressBar.setProgress(values[0]);
                tv_progress.setText(values[0]+"");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(), "备份完成", Toast.LENGTH_LONG).show();
                isRunning= false;
            }
        }.execute();
        isRunning = true;
    }
}
