package com.myApk.ui.main.item3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.myApk.R;


/**
    使用TweenedAnimations的步骤：
    1创建一个AnimationSet对象（Animation子类）;
    2增加需要创建相应的Animation对象
    3更加项目需求，为Animation对象设置相应的数据
    4将Animation对象添加到AnimationSet对象当中
    5使用控件对象开始执行AnimaitonSet

     Tweened 分类
     １、Alpha：淡入淡出效果
 　　２、Scale：缩放效果
 　　３、Rotate：旋转效果
 　　４、Translate：移动效果

    设置动画持续时间（单位：毫秒）
    1 setDuration(long durationMills);
    如果fillAfter 值为true,则动画执行后，控件将停留在挂靠结束的状态
    2  setFillAfter(Boolean fillBefore);
    如果fillAfter 值为true,则动画执行后，控件将回支动画执行之前的状态
    3 setFillBefore(Boolean fileBefore);
    设置动画执行之前的等待时间
    4 setStartOffSet(long startOffSet);
    设置动画重复执行的次数
    setRepeatCount(int repeatCount);
 */
public class AnimationsActivit extends Activity implements View.OnClickListener
{
    Context mContext;

    private Button btn_rotate;
    
    private Button btn_scale;
    
    private Button btn_alpha;
    
    private Button btn_translate;

    private Button  btn_complex;

    private Button btn_rotate2;

    private Button btn_scale2;

    private Button btn_alpha2;

    private Button btn_translate2;

    private Button btn_complex2;

    private ImageView img;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);
        mContext=this;
        initView();
    }
    
    private void initView()
    {
        btn_rotate = (Button) findViewById(R.id.btn_rotate);
        btn_rotate.setOnClickListener(this);
        btn_scale = (Button) findViewById(R.id.btn_scale);
        btn_scale.setOnClickListener(this);
        btn_alpha = (Button) findViewById(R.id.btn_alpha);
        btn_alpha.setOnClickListener(this);
        btn_translate = (Button) findViewById(R.id.btn_translate);
        btn_translate.setOnClickListener(this);
        btn_complex = (Button) findViewById(R.id.btn_complex);
        btn_complex.setOnClickListener(this);
        img = (ImageView) findViewById(R.id.image);

        btn_rotate2 = (Button) findViewById(R.id.btn_rotate2);
        btn_rotate2.setOnClickListener(this);
        btn_scale2 = (Button) findViewById(R.id.btn_scale2);
        btn_scale2.setOnClickListener(this);
        btn_alpha2 = (Button) findViewById(R.id.btn_alpha2);
        btn_alpha2.setOnClickListener(this);
        btn_translate2 = (Button) findViewById(R.id.btn_translate2);
        btn_translate2.setOnClickListener(this);
        btn_complex2 = (Button) findViewById(R.id.btn_complex2);
        btn_complex2.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_rotate:
                rotateAnimation();
                break;
            case R.id.btn_rotate2:
                // 使用AnimationUtils装载动画配置文件
                 Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.rotate);
                img.startAnimation(animation);
                break;
            
            case R.id.btn_scale:
                scaleAnimation();
                break;
            case R.id.btn_scale2:
                // 使用AnimationUtils装载动画配置文件
                  animation = AnimationUtils.loadAnimation(mContext,R.anim.scale);
                img.startAnimation(animation);
                break;

            case R.id.btn_alpha:
                alphaAnimation();
                break;
            case R.id.btn_alpha2:
                // 使用AnimationUtils装载动画配置文件
                animation = AnimationUtils.loadAnimation(mContext,R.anim.alpha);
                img.startAnimation(animation);
                break;
            
            case R.id.btn_translate:
                translateAnimation();
                break;
            case R.id.btn_translate2:
                // 使用AnimationUtils装载动画配置文件
                animation = AnimationUtils.loadAnimation(mContext,R.anim.translate);
                img.startAnimation(animation);
                break;

            case R.id.btn_complex:
                complexAnimation();
                break;
            case R.id.btn_complex2:
                animation = AnimationUtils.loadAnimation(mContext,R.anim.complex);
                img.startAnimation(animation);
                break;
        }
    }

    /**
     * 动画监听
     */
    private class  DIYAnimationListener implements Animation.AnimationListener{
        /**开始*/
        @Override
        public void onAnimationStart(Animation animation) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                img.setBackgroundDrawable(getResources().getDrawable(R.drawable.videos_by_google_icon));
            } else {
                img.setBackgroundDrawable(getResources().getDrawable(R.drawable.videos_by_google_icon));
            }
            img.setBackgroundResource(R.drawable.abc_btn_check_material);
//            img.setBackgroundColor(android.graphics.Color.parseColor("#92001c"));
//            img.setBackgroundColor(Color.rgb(255, 0, 0));
            img.setBackgroundColor(R.color.red);
        }
        /**结束*/
        @Override
        public void onAnimationEnd(Animation animation) {
            img.setBackgroundColor(Color.YELLOW);
        }

        /**重复*/
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    /**
     * 旋转
     */
    private void rotateAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        /**
         * 1从那旋转角度开始
         * 2转到什么角度
         后4个参数用于设置围绕着旋转的圆的圆心在哪里
         * 3确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
         * 4x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
         * 5确定y轴坐标的类型
         * 6y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
         */
        RotateAnimation rotateAnimation = new RotateAnimation(0,180,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);

//        设置动画持续时间（单位：毫秒）
        rotateAnimation.setDuration(1000);
//       控件将停留在挂靠结束的状态
        rotateAnimation.setFillAfter(true);
//       控件将回动画执行之前的状态
        rotateAnimation.setFillBefore(false);
//        设置动画执行之前的等待时间
        rotateAnimation.setStartOffset(500);
//        设置动画重复执行的次数
        rotateAnimation.setRepeatCount(0);

        animationSet.addAnimation(rotateAnimation);
        animationSet.setAnimationListener( new DIYAnimationListener());
        img.startAnimation(animationSet);
    }


    /***
     * 绽放
     */
    private void scaleAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        /**
         * 1x轴的初始值
         * 2x轴收缩后的值
         * 3y轴的初始值
         * 4y轴的缩后的值
         * 5确定x轴坐标的类型
         * 6x轴的值，0.5表明是以自身这个控件的一半长度为x轴
         * 7确定y轴坐标的类型
         * 8y轴的值，0.5表明是以自身这个控件的一半长度为y轴
         */
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,0.1f,0,0.1f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(2000);
        animationSet.addAnimation(scaleAnimation);
        img.startAnimation(animationSet);
    }

    /**
     * 淡入淡出
     */
    private void alphaAnimation() {
        //创建一个AnimationSet对象，参数为Boolean型，
        //true表示使用Animation的interpolator，false则是使用自己的
        AnimationSet animationSet = new AnimationSet(true);
        //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        //设置动画的时间
        alphaAnimation.setDuration(1000);
        //将alphaAnimation对象添加到AnimationSet当中
        animationSet.addAnimation(alphaAnimation);
        //使用ImageView的startAnimiton方法执行动画
        img.startAnimation(animationSet);
    }


    /**
     * 移动
     */
    private void translateAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        /**
         * 12 x轴的开始位置
         * 34 y轴的开始位置
         * 56 x轴的结束位置
         * 78 y轴的结束位置
         */
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0.5f);
        translateAnimation.setDuration(2000);
        animationSet.addAnimation(translateAnimation);
        img.startAnimation(animationSet);
    }

    private void complexAnimation()
    {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,0.1f,0,0.1f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(2000);
        animationSet.addAnimation(scaleAnimation);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0.5f);
        translateAnimation.setDuration(2000);
        animationSet.addAnimation(translateAnimation);

        img.startAnimation(animationSet);
    }

}
