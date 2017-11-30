package com.myApk.ui.main.setting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.myApk.R;
import com.myApk.common.GlobalMessageType;
import com.myApk.ui.view.itemView.SettingItemView;
import com.myApk.uitl.ConfigUtil;

/**
 * 设置中心
 */
public class SettingCenterActivity extends Activity
{
    private SettingItemView siv_update;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_conent);
        siv_update= (SettingItemView) findViewById(R.id.siv_update);
        context=this;
//        siv_update.updateStatus(flag);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                siv_update.updateStatus();
                if (siv_update.getCb_state().isChecked()){
                    ConfigUtil.putBoolean(context, GlobalMessageType.SharedFileKey.UPDATE_SOFTWARE, true);
                    Log.i("------------",":"+ConfigUtil.getBoolean(context, GlobalMessageType.SharedFileKey.UPDATE_SOFTWARE));
                }
                else{
                    ConfigUtil.putBoolean(context, GlobalMessageType.SharedFileKey.UPDATE_SOFTWARE,false);
                    Log.i("------------",":"+ConfigUtil.getBoolean(context, GlobalMessageType.SharedFileKey.UPDATE_SOFTWARE));
                }
            }
        });
    }
}
