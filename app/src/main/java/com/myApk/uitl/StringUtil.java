package com.myApk.uitl;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符工具
 */
public class StringUtil
{
    /**
     * 是否为null 或空字符串
     * @param str
     * @return
     */
    public static  boolean isNotEmpty(String str)
    {
        if (str != null || !"".equals(str.trim()))
        {
            return true;
        }
        return false;
    }

    /**
     * 用于判断字符是否是中文
     * @param str
     * @return
     */
    public static boolean isCN(String str)
    {
        try {
            byte [] bytes = str.getBytes("UTF-8");
            if (bytes.length == str.length()){
                return false;
            }else{
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是否是手机号
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumber(String phoneNumber)
    {
         if (StringUtil.isNotEmpty(phoneNumber))
         {
             String regExp = "^(13[0-9]|14[0-9]|15[0-9]|170|176|177|178)[0-9]{8}$";
             Pattern p = Pattern.compile(regExp);
             Matcher m = p.matcher(phoneNumber);
             return m.find();
         }
         return false;
    }

    /**
     * 判断是否是数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str)
    {
        String reg = "[0-9]+";
        return str.matches(reg);
    }

    /**
     * 判断是否是邮箱
     * @param str
     * @return
     */
    public static boolean isEmail(String str)
    {
        String reg ="^([a-zA-Z0-9_\\-\\.]+)@(\\[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断字符是否数字
     * @param str
     * @return
     */
    public static boolean isDigital(String str)
    {
        return str.matches("(-)?\\d+");
    }

    /**
     * 判断是否带小数点
     * @param str
     * @return
     */
    public static boolean isDouble(String str)
    {
        if (isDigital(str))
        {
            return true;
        }
        return str.matches("(-)?\\d+\\.\\d+");
    }

    /**
     * 返回特定长度的随机数
     * @param length
     * @return
     */
    public static String getRadomNum(int length)
    {
        length  = length > 0 ? length : 10;
        StringBuffer sRand = new StringBuffer();
        SecureRandom mRandom = new SecureRandom();
        for (int i = 0; i<length;i++)
        {
            sRand.append((int)mRandom.nextDouble() * 10);
        }
        return mRandom.toString();
    }

    /**
     * 对敏感字符串以给定的字符替换
     * @param text
     * @param replaceStr
     * @return
     */
    public static String fuzzySensitiveText(String text,String replaceStr)
    {
        String result = null;
        if (StringUtil.isNotEmpty(text) && StringUtil.isNotEmpty(replaceStr))
        {
            int len = text.length();
            if (len > 2)
            {
                int gap = Math.round(len/3);
                int partOneLen = len - gap * 2;
                String regExp = "(?<=.{" + String.valueOf(partOneLen) + "}).(?=.{" + String.valueOf(gap) + "})";;
                result = text.replaceAll(regExp,replaceStr);
            }
        }
        return  result;
    }


























}
