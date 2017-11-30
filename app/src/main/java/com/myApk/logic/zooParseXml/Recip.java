package com.myApk.logic.zooParseXml;

import com.myApk.uitl.StringUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by admin on 2016/2/22.
 */
public class Recip {
    public String name;
    public String desic;
    public int price;

    public void parse(XmlPullParser parser, String endTag) {
        try {
           boolean keepParseing = true;
            while (keepParseing)
            {
                int enentType = parser.getEventType();
                String nodeName = parser.getName();
                switch (enentType)
                {
                    case XmlPullParser.START_TAG:
                            if ("name".equals(nodeName))
                            {
                                String nextStr = parser.nextText();
                                this.name = StringUtil.isNotEmpty(nextStr) ? nextStr : null;
                            }else if ("desic".equals(nodeName))
                            {
                                String nextStr = parser.nextText();
                                this.desic =StringUtil.isNotEmpty(nextStr) ? nextStr : null;
                            }else if ("price".equals(nodeName))
                            {
                                String nextStr = parser.nextText();
                                this.price = StringUtil.isNotEmpty(nextStr) ? Integer.parseInt(nextStr) : null;
                            }
                        break;
                    case XmlPullParser.END_TAG:
                        if (!nodeName.equals(endTag))
                        {
                            break;
                        }
                        keepParseing = false;
                        break;
                }
                if (!keepParseing)
                {
                   continue;
                }
                parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
