package com.myApk.logic.zooParseXml;

import android.util.Xml;

import com.myApk.uitl.StringUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2016/2/22.
 */
public class Zoo {
    public int resultCode;
    public  AnimalList animalList  ;

    public  void parse(InputStream is)
    {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is,"UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String nodenName = parser.getName();
                        if ("result".equals(nodenName))
                        {
                            String codeStr = parser.getAttributeValue(null,"resultCode");
                            if (StringUtil.isNotEmpty(codeStr))
                            {
                                this.resultCode = Integer.parseInt(codeStr);
                            }
                        }else if ("animalList".equals(nodenName))
                        {
                            this.animalList = new AnimalList();
                            this.animalList.parse(parser,nodenName);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
