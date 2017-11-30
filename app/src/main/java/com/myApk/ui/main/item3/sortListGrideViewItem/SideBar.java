package com.myApk.ui.main.item3.sortListGrideViewItem;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.myApk.R;

/**
 * Created by admin on 2015/9/25.
 */
public class SideBar extends View {
    //触摸事件
    private OnTouchLetterCheckedChangeListener onTouchLetterCheckedChangeListener;
    // 26个字母
    public static String[] b ={"A","B","C","D","D","E","F","G","H","I","J","K","L",
            "M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};
    //选中
    private int choose = -1;

    private Paint paint = new Paint();
    private TextView tvDialog;

    public void setTvDialog(TextView tvDialog) {
        this.tvDialog = tvDialog;
    }

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context,AttributeSet attributeSet) {
        super(context,attributeSet);
    }

    public SideBar(Context context,AttributeSet attributeSet,int defStyle) {
        super(context,attributeSet,defStyle);
    }

    /**重写这个方法*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取焦点改变背景颜色
        int height = getHeight();//获取对应高度
        int width = getWidth();//获取对应宽度
        int singleHeight = height/b.length;//获取每一个字母的高度

        for (int i=0;i<b.length;i++){
            paint.setColor(Color.rgb(33,65,98));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(40);
            //选中的状态
            if (i == choose){
                paint.setColor(getResources().getColor(R.color.green_light));
                paint.setTextSize(60);
                paint.setFakeBoldText(true);
            }
            //x坐标等于中间字符串宽度的一半
            float xPos = width / 2 - paint.measureText(b[i]) /2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i],xPos,yPos,paint);
            paint.reset();//重置画笔
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();//点击y坐标
        final int oldChoose = choose;
        final OnTouchLetterCheckedChangeListener listener = onTouchLetterCheckedChangeListener;
        final int c = (int) (y / getHeight() * b.length);//点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        switch (action)
        {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;
                invalidate();
                if (tvDialog != null){
                    tvDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                //背景色
                setBackgroundColor(getResources().getColor(R.color.gray));
                if (oldChoose != c){
                    if (c >= 0 && c < b.length){
                        listener.OnTouchLetterCheckedChangeListener(b[c]);
                    }
                    if (tvDialog != null){
                        tvDialog.setText(b[c]);
                        tvDialog.setVisibility(View.VISIBLE);
                    }
                    choose = c;
                    invalidate();
                }
        }
        return true;
    }

    public  void setOnTouchLetterCheckedChangeListener( OnTouchLetterCheckedChangeListener onTouchLetterCheckedChangeListener )
    {
        this.onTouchLetterCheckedChangeListener = onTouchLetterCheckedChangeListener;
    }
    /**接口*/
    public interface OnTouchLetterCheckedChangeListener{
        public void OnTouchLetterCheckedChangeListener(String str);
    }
}
