package com.myApk.ui.main.item3;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Telephony;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myApk.R;
import com.myApk.receiver.SmsInterceptBroadcast;
import com.myApk.logic.MsgListener;


public class EditTextActivity extends Activity implements View.OnClickListener
{
    private final static String TAG="PayActivity";

    private TextView accountsInfo;

    private EditText smsPayVerificationCode;//短信付费码验证码
    private Button btnVerificationCode;//获取付费码按钮

    private RelativeLayout rlVerification;//验证码
    private EditText verificationCode; //图片验证码
    private ImageView imgVerificationCode;//图片验证码图
    private ImageView nextVerification;//下一张图
    private RelativeLayout rlNextVerification ;//换一张
    private TextView originalPrice;//原价
    private  TextView discountPrice;//折扣价
    private Button btnPay;//付费


    private String phoneNumber;
    private Context mCotext;

    private TimeCount time;
    private String  verifyPic;//图片验证码

    private String smsCode;
    private String picCode;

    private CheckBox checkbox_switch;

    /**
     * 标题
     */
    private TextView title;

    private View titleBackLayout;


    private Dialog confirmDialog;


    private PopupWindow mMoreWindow;


    SmsInterceptBroadcast receiver = new SmsInterceptBroadcast();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);
        this.mCotext=this;
        phoneNumber = "1234";

        time = new TimeCount(5000, 1000);//构造CountDownTimer对象
        initView();

        initPopupWindow();


        confirmDialog = new Dialog(this,R.style.dialog);
    }

    /**
     * 更多按钮菜单
     */
    private void initPopupWindow() {
        View view = View.inflate(mCotext, R.layout.popupwindow_menu, null);
        RelativeLayout specification= (RelativeLayout) view.findViewById(R.id.specification);
        specification.setOnClickListener(this);

        RelativeLayout about_us= (RelativeLayout) view.findViewById(R.id.about_us);
        about_us.setOnClickListener(this);

        DisplayMetrics dm = new DisplayMetrics();
        //当高度小于宽度时，设置宽度的值为高度
        int deviceWidth = Math.min(dm.widthPixels, dm.heightPixels);
        //当宽度大于高度时，设置高度的值为宽度
       int deviceHeight = Math.max(dm.widthPixels, dm.heightPixels);

        mMoreWindow = new PopupWindow(view,
                ( deviceWidth < deviceHeight) ?  deviceWidth / 2
                        : deviceHeight/ 2,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mMoreWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        mMoreWindow.setAnimationStyle(R.style.popwin_anim_down_style);
        mMoreWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mMoreWindow.setOutsideTouchable(false);;
//        mMoreWindow.showAsDropDown(view);
    }

    private void showMenuWindow()
    {
        if (mMoreWindow != null && mMoreWindow.isShowing())
        {
            mMoreWindow.dismiss();
        }else{
            int  displayPopupWindowX = getWindowManager().getDefaultDisplay().getWidth();
            mMoreWindow.showAsDropDown(findViewById(R.id.set_pay_title),
                    displayPopupWindowX,
                    0);
        }
    }

    private void initView()
    {
        registerSMSBroadcast();

        title = (TextView) findViewById(R.id.tv_title);
        title.setText("支付");
        LinearLayout more= (LinearLayout) findViewById(R.id.more);
        more.setOnClickListener(this);

        titleBackLayout = (View) findViewById(R.id.btn_back);
        accountsInfo= (TextView) findViewById(R.id.tv_visible);
        accountsInfo.setOnClickListener(this);
        smsPayVerificationCode= (EditText) findViewById(R.id.et_pay_verification_code);


        btnVerificationCode= (Button) findViewById(R.id.btn_verification_code);
        btnVerificationCode.setEnabled(false);
        btnVerificationCode.setOnClickListener(this);


        rlVerification= (RelativeLayout) findViewById(R.id.rl_verification);
        rlVerification.setVisibility(View.VISIBLE);


        verificationCode= (EditText) findViewById(R.id.et_img_verification_code);
        verificationCode.addTextChangedListener(watcher);


        imgVerificationCode= (ImageView) findViewById(R.id.iv_img_verification_code);
        nextVerification= (ImageView) findViewById(R.id.img_next_verification);
        rlNextVerification= (RelativeLayout) findViewById(R.id.rl_next);
//        rlNextVerification.setOnClickListener(this);
        originalPrice= (TextView) findViewById(R.id.original_price );
        discountPrice= (TextView) findViewById(R.id.pay_discount_price);

        checkbox_switch= (CheckBox) findViewById(R.id.checkbox_switch);
        checkbox_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean chcek)
            { // TODO Auto-generated method stub
                if (chcek){
                    Toast.makeText(EditTextActivity.this,"选中了 ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(EditTextActivity.this,"取消了 ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * 注册短信监听
     */
    private void registerSMSBroadcast() {
        SmsInterceptBroadcast.setMsgListener(msgListener);
        SmsInterceptBroadcast.setIsIntercept(true);
//        SmsBroadCastReceiver.setMsgListener(msgListener);

        //添加过虑器  动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(receiver, filter);
    }

    MsgListener msgListener = new MsgListener() {
        @Override
        public void onMsg(String msg, boolean success) {
            if (success){
                title.setText(msg);
                Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
            }
            else {
                title.setText("失败");
            }
        }

        @Override
        public void onCancel() {

        }
    };


    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            picCode=verificationCode.getText().toString().trim();
            if(picCode.length() >0 )
            {
                btnVerificationCode.setEnabled(true);
            }else{
                btnVerificationCode.setEnabled(false);
            }
        }
    };

    @Override
    public void onClick(View view) {
        int id= view.getId();
        switch (id){
            case R.id.tv_visible:
                    isShow();
                break;
            case R.id.btn_verification_code:
                showVerifyPicDialog();
                time.start();
                break;
            case R.id.more:
                Toast.makeText(mCotext,"!@#!@",Toast.LENGTH_LONG).show();
                showMenuWindow();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void isShow() {
        if(View.VISIBLE == rlVerification.getVisibility()){
            rlVerification.setVisibility(View.GONE);
            btnVerificationCode.setEnabled(true);
        }else{
            rlVerification.setVisibility(View.VISIBLE);
            picCode=verificationCode.getText().toString().trim();
            if(picCode.length() >0 )
            {
                btnVerificationCode.setEnabled(true);
            }else{
                btnVerificationCode.setEnabled(false);
                verificationCode.setFocusableInTouchMode(true);//设置图表验证框获得焦点
                verificationCode.requestFocus();
            }
        }
    }


    private void showVerifyPicDialog()
    {
        View dlg = LayoutInflater.from(EditTextActivity.this).inflate(R.layout.dialog_diy,null);
        imgVerificationCode = (ImageView) dlg.findViewById(R.id.iv_img_verification_code);
        rlNextVerification = (RelativeLayout) dlg.findViewById(R.id.rl_next);
        nextVerification = (ImageView) dlg.findViewById(R.id.img_next_verification);
        rlNextVerification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                 Toast.makeText(EditTextActivity.this,"what",Toast.LENGTH_LONG).show();
            }
        });



        //确定
        dlg.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //取消
        dlg.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
            }
        });

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        lp.width = width * 203 /240;
        confirmDialog.setContentView(dlg,lp);
        confirmDialog.setCanceledOnTouchOutside(false);
        confirmDialog.show();
    }



    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer
    {
        public TimeCount(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            confirmDialog.dismiss();
            accountsInfo.setText("开/关");
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示 state_enabled

            accountsInfo.setText(millisUntilFinished / 1000 + "秒");
            accountsInfo.setTextColor(R.color.red);
//            btnVerificationCode.setClickable(false);
//            btnVerificationCode.setEnabled(false);
        }
    }

}