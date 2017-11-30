package com.myApk.ui.main;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.myApk.R;
import com.myApk.framework.app.BaseV4Fragment;
import com.myApk.ui.main.item3.AnimationsActivit;
import com.myApk.ui.main.item3.AsyncTaskActivit;
import com.myApk.ui.main.item3.EditTextActivity;
import com.myApk.ui.main.item3.GridViewActivity;
import com.myApk.ui.main.item3.NewsActivity;
import com.myApk.ui.main.item3.NotificationDIY;
import com.myApk.ui.main.item3.ProgressDialog;
import com.myApk.ui.main.item3.ShareActivity;
import com.myApk.ui.main.item3.TwoDimensionCode;
import com.myApk.ui.main.item3.TwoMenuActivit;
import com.myApk.ui.main.item3.WebViewActivity;
import com.myApk.ui.main.item3.WebViewHellpActivity;
import com.myApk.ui.main.setting.SettingCenterActivity;
import com.zbar.lib.CaptureActivity;


/**
 * 3
 */
public class RecommendedFragment extends BaseV4Fragment implements
        View.OnClickListener
{
    public  Context context;
    private  Dialog dlg;//蒙版
    private Dialog confirmDialog; //确认 对话框
    private RelativeLayout rl_myListViewDiy;
    private RelativeLayout rl_diy_dialog;
    private RelativeLayout rl_progress_dialog;
    private RelativeLayout rl_edittext;
    private RelativeLayout rl_animations;
    private RelativeLayout rl_two_menu;
    private RelativeLayout rl_two_dimension_code;
    private RelativeLayout rl_share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootview = inflater.inflate(R.layout.fragment_recommended, container,  false);
        context = getActivity();

        confirmDialog = new Dialog(context,R.style.dialog);

        rl_myListViewDiy = (RelativeLayout) rootview.findViewById(R.id.rl_myListViewDiy);
        rl_myListViewDiy.setOnClickListener(this);

        rl_diy_dialog=(RelativeLayout) rootview.findViewById(R.id.rl_diy_dialog);
        rl_diy_dialog.setOnClickListener(this);

        rl_progress_dialog=(RelativeLayout) rootview.findViewById(R.id.rl_progress_dialog);
        rl_progress_dialog.setOnClickListener(this);

        rl_edittext=(RelativeLayout) rootview.findViewById(R.id.rl_edittext);
        rl_edittext.setOnClickListener(this);

        rl_animations = (RelativeLayout) rootview.findViewById(R.id.rl_animations);
        rl_animations.setOnClickListener(this);


        rl_two_menu = (RelativeLayout) rootview.findViewById(R.id.rl_two_menu);
        rl_two_menu.setOnClickListener(this);


        rl_two_dimension_code= (RelativeLayout) rootview.findViewById(R.id.rl_two_dimension_code);
        rl_two_dimension_code.setOnClickListener(this);

        rl_share= (RelativeLayout) rootview.findViewById(R.id.rl_share);
        rl_share.setOnClickListener(this);

        rootview.findViewById(R.id.rl_notification).setOnClickListener(this);
        rootview.findViewById(R.id.rl_capture).setOnClickListener(this);

        rootview.findViewById(R.id.rl_webview).setOnClickListener(this);
        rootview.findViewById(R.id.rl_webview_assets_html).setOnClickListener(this);
        rootview.findViewById(R.id.rl_AsyncTask).setOnClickListener(this);
        rootview.findViewById(R.id.rl_gridview).setOnClickListener(this);
        rootview.findViewById(R.id.rl_question).setOnClickListener(this);


        return rootview;
        
    }
    
    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.rl_myListViewDiy:
                Intent intent = new Intent(context, NewsActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_diy_dialog:
                showDiyDialog();
                break;
            case R.id.rl_progress_dialog:
                dlg = new ProgressDialog(context);
                dlg.show(); //关dlg.dismiss();
                break;
            case R.id.rl_edittext:
                intent = new Intent(context,EditTextActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_animations:
                intent = new Intent(context,AnimationsActivit.class);
                startActivity(intent);
                    break;
            case R.id.rl_two_menu:
                intent = new Intent(context,TwoMenuActivit.class);
                startActivity(intent);
                break;
            case R.id.rl_two_dimension_code:
                intent = new Intent(context,TwoDimensionCode.class);
                startActivity(intent);

                break;
            case R.id.rl_share:
                intent = new Intent(context,ShareActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_notification:
                NotificationDIY.createNotification(context);
                break;
            case R.id.rl_capture:
                intent = new Intent(context,CaptureActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_webview:
                intent = new Intent(context,WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_webview_assets_html:
                intent = new Intent(context,WebViewHellpActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_AsyncTask:
                intent = new Intent(context,AsyncTaskActivit.class);
                startActivity(intent);
                break;

            case R.id.rl_gridview:
                intent = new Intent(context,GridViewActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_question:
                intent = new Intent(context,SettingCenterActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void showDiyDialog() {
        View dlgDiy = LayoutInflater.from(context).inflate(R.layout.dialog_diy,null);
        final ImageView imgVerificationCode = (ImageView) dlgDiy.findViewById(R.id.iv_img_verification_code);
        RelativeLayout rlNextVerification = (RelativeLayout) dlgDiy.findViewById(R.id.rl_next);
        ImageView nextVerification = (ImageView) dlgDiy.findViewById(R.id.img_next_verification);
        final EditText et_img_verification_code = (EditText) dlgDiy.findViewById(R.id.et_img_verification_code);
        rlNextVerification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                imgVerificationCode.setBackgroundColor(R.color.red);
            }
        });

        //确定

        dlgDiy.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
                String picCode = et_img_verification_code.getText().toString().trim();
            }
        });
        //取消
        dlgDiy.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
            }
        });

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        lp.width = width * 203 /240;
        confirmDialog.setContentView(dlgDiy,lp);
        confirmDialog.setCanceledOnTouchOutside(false);
        confirmDialog.show();
    }

    @Override
    protected void initLogics() {

    }
}
