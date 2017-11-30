package com.myApk.ui.main.item3;
import android.app.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.myApk.uitl.EncodingHandler;
import com.myApk.R;
import com.google.zxing.WriterException;


/**
 * 二维码生成
 */
public class TwoDimensionCode extends Activity
{
    private ImageView iv_two_dimension_code;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_dimension_code);
        initTextView();
    }

    private void initTextView()
    {
        iv_two_dimension_code = (ImageView) findViewById(R.id.iv_two_dimension_code);
        String   val = "http://caiyun.feixin.10086.cn:7070/portal/client.jsp";


        try
        {
            float h = getResources().getDimension(R.dimen.display_bytype_window_width);
            Bitmap bmp = EncodingHandler.createQRCode(val, (int) h);
            iv_two_dimension_code.setImageBitmap(bmp);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
        
    }
}
