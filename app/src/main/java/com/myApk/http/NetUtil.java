package com.myApk.http;


import android.os.Message;

import com.myApk.common.GlobalMessageType;
import com.myApk.framework.utils.MessageCenter;
import com.myApk.model.News;
import com.sina.weibo.sdk.utils.LogUtil;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * 提供一些方法获取数据
 * Created by admin on 2015/9/11.
 */
public class NetUtil {
    public final static  String UESRAGENT_AGENT = "User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A405 Safari/8536.25";

    public final static  String UESRAGENT_PHONE = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A405 Safari/8536.25";



    public static String postAndGetData(String url)
    {
        String response = null;
        try {
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.setHeader("User-Agent", UESRAGENT_AGENT);
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//            if (200 == httpResponse.getStatusLine().getStatusCode())
//            {
//                response = EntityUtils.toString(httpResponse.getEntity());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 通过URL请求得到json 数组
     */
    public static List<News>  getJsonObjectsByUrl(String newsName,int page)
    {
        String url="http://baidu.com/news?tn=bdapisearch&word="+newsName+"&pn="+20*page+"&rn=20&t=1386838893136";
        List<News> list = new ArrayList<News>();
        try {
            String response = postAndGetData(url);
            response = response.replace("&quot;", "\'");
            JSONObject jsonObject = new JSONObject(response);
            Object tableValue = jsonObject.get("list");
            if (tableValue instanceof JSONArray) {
                JSONArray valueJson = new JSONArray(tableValue.toString());
                if (valueJson.length() > 0) {
                    for (int j = 0; j < valueJson.length(); j++) {
                          jsonObject = new JSONObject(valueJson.getString(j));
                        News news = new News();
                        news.title = jsonObject.getString("title");
                        news.abs = jsonObject.getString("abs");
                        news.source = jsonObject.getString("author");
                        news.url = jsonObject.getString("url");
                        news.photoUrl = jsonObject.getString("imgUrl");
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CANADA);
                        news.date = df.format(new Date(jsonObject.getLong("sortTime")*1000));
                        list.add(news);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        } catch (Exception e) {
            LogUtil.i("NewUtil", e.toString());

        }
        if (list.size() > 0) {
            Message msg = new Message();
            msg.what = GlobalMessageType.TypeMsg.load_service_success;
            msg.obj = list;
            MessageCenter.getInstance().sendMessage(msg);
        }
        return list;
    }

    /**
     * 通过URL请求得到json
     * @param url
     * @return
     */
    public static JSONObject getJsonObjectByUrl(String url)
    {
        try{
            String response = postAndGetData(url).replace("&quot;","\'");
            JSONObject jsonObject = new JSONObject(response);
            return  jsonObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Document getDocumentByUrl(String url)
    {
        try {
            Document document = Jsoup.connect(url).userAgent(UESRAGENT_PHONE).get();
            return document;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
