package com.myApk.ui.main.item3.tabHostGridViewItem;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myApk.R;

/**
 * Created by admin on 2015/10/21.
 */
public class FXActivity extends Activity implements OnClickListener {
    private ImageView refreshing_image;
    private ProgressBar refresh_prgressBar;
   private TextView waiting_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fx);
        refreshing_image = (ImageView) findViewById(R.id.refresh_image);
        refreshing_image.setOnClickListener(this);
        refresh_prgressBar = (ProgressBar) findViewById(R.id.refresh_progressbar);
        waiting_progress = (TextView) findViewById(R.id.waiting_progress);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.refresh_image:
                refresh();

        }
    }

    //防止第二次点击
    public boolean isRefreshing = false;
    public void refresh()
    {
        if (isRefreshing){return;}

        new AsyncTask<Void, Integer, Void>() {
            int progress =0;

            //任务开始前
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                refresh_prgressBar.setVisibility(View.VISIBLE);
                refresh_prgressBar.setProgress(0);
                waiting_progress.setVisibility(View.VISIBLE);
                waiting_progress.setText(progress+"%");
            }

            //任务执行中
            @Override
            protected Void doInBackground(Void... voids) {
                for (int i=0;i<100;i++){
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

            //进度发生变化更改界面
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                refresh_prgressBar.setProgress(values[0]);
                waiting_progress.setText(values[0]+"%");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                refresh_prgressBar.setVisibility(View.GONE);
                waiting_progress.setVisibility(View.GONE);
                isRefreshing = false;
            }
        }.execute();
        isRefreshing = true;
    }
}
