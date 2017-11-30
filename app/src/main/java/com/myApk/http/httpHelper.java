package com.myApk.http;

import android.util.Log;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/8/3.
 */
public class httpHelper
{
    
    public static void requestByHttpGet(String url)
    {
//        try
//        {
//            HttpGet httpGet = new HttpGet(url);
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//            if (200 == httpResponse.getStatusLine().getStatusCode())
//            {
//                String result = EntityUtils.toString(httpResponse.getEntity(),
//                        "UTF-8");
//                Log.i("--->", result);
//            }
//            else
//            {
//                Log.i("--->", "请求失败");
//            }
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
    }
    
    public static void requestByHttpPost(String url)
    {
        try
        {
//            HttpPost httpPost = new HttpPost(url);
//            List<NameValuePair> parmas = new ArrayList<NameValuePair>();
//            // 设置参数
//            parmas.add(new BasicNameValuePair("id", "helloworld"));
//            parmas.add(new BasicNameValuePair("pwd", "android"));
//            // 设置字符集
//            HttpEntity entity = new UrlEncodedFormEntity(parmas, HTTP.UTF_8);
//            // 设置参数实体
//            httpPost.setEntity(entity);
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//            if (200 == httpResponse.getStatusLine().getStatusCode())
//            {
//                String result = EntityUtils.toString(httpResponse.getEntity(),
//                        "UTF-8");
//            }
//            else
//            {
//                Log.i("--->", "请求失败");
//            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void requestByGet(String path)
    {
        try
        {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            connection.setConnectTimeout(5 * 1000);
            connection.connect();
            if (200 == connection.getResponseCode())
            {
                byte[] data = readStream(connection.getInputStream());
            }
            else
            {
                Log.i("--->", "请求失败");
            }
            // 关闭连接
            connection.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static byte[] readStream(InputStream inputStream)
    {
        
        return new byte[0];
    }
    
    public static void requestByPost(String rul)
    {
        try
        {
            String params = "id=" + URLEncoder.encode("helloworld", "UTF-8")
                    + "&pwd=" + URLEncoder.encode("android", "UTF-8");
            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(rul);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            // Post请求必须设置允许输出
            urlConn.setDoOutput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            urlConn.setInstanceFollowRedirects(true);
            // 配置请求Content-Type
            urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencode");
            // 开始连接
            urlConn.connect();
            // 发送请求参数
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200)
            {
                // 获取返回的数据
                byte[] data = readStream(urlConn.getInputStream());
                Log.i("---->", "Post请求方式成功，返回数据如下：");
                Log.i("---->", new String(data, "UTF-8"));
            }
            else
            {
                Log.i("---->", "Post方式请求失败");
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
