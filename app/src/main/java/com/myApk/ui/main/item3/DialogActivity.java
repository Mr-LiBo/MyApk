package com.myApk.ui.main.item3;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myApk.R;


/**
 * Created by admin on 2015/7/30.
 */
public class DialogActivity extends Activity
{
     private TextView tv_window;
    Dialog confirmDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog);

        confirmDialog = new Dialog(this,R.style.dialog);
        showDiyDialog();
//        tv_window= (TextView) findViewById(R.id.tv_window);
//        tv_window.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDiyDialog();
//            }
//
//
//        });
    }

    private void showDiyDialog() {
        View dlgDiy = LayoutInflater.from(DialogActivity.this).inflate(R.layout.dialog_diy,null);
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
        WindowManager wm = (WindowManager)DialogActivity.this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        lp.width = width * 203 /240;
        confirmDialog.setContentView(dlgDiy,lp);
        confirmDialog.setCanceledOnTouchOutside(false);
        confirmDialog.show();
    }

}
