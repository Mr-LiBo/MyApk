package com.myApk.uitl;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;


/**
 * 二维码生成
 */
public class EncodingHandler
{
    private static final int BLACK = 0xff000000;

    private static final int WHITE = 0xffffffff;
    public static Bitmap createQRCode(String str,int widthAndHeight) throws WriterException {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        boolean start = false;

        int startX = -1;
        int startY = -1;

        int endX = -1;
        int endY = -1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                    if (!start) {
                        start = true;
                        startX = x;
                        startY = y;
                    }
                    if (y == startY)
                    {
                        endX = -1;
                    }
                    if (x == startX)
                    {
                        endY = -1;
                    }
                }
                else if (y == startY)
                {
                    pixels[y * width + x] = WHITE;
                    if (endX == -1) {
                        endX = x - 1;
                    }
                }
                else if (x == startX)
                {
                    pixels[y * width + x] = WHITE;
                    if (endY == -1) {
                        endY = y - 1;
                    }
                }
                else
                {
                    pixels[y * width + x] = WHITE;
                }
//                else if (start && x >= startX && (endX == -1 || x <= endX) && endY == -1) {
//                        pixels[y * width + x] = WHITE;
//                    }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(endX - startX + 1, endY - startY + 1,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, startY * width + startX, width, 0, 0, endX - startX + 1, endY - startY + 1);
        return bitmap;
    }
}
