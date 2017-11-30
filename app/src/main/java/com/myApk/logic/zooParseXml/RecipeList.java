package com.myApk.logic.zooParseXml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/2/22.
 */
public class RecipeList {
    public List<Recip> recipList = new ArrayList<>();

    public void parse(XmlPullParser parser, String endTag)
    {

        try {
            boolean keepParseing = true;
            while (keepParseing)
            {
                int eventType = parser.getEventType();
                String nodeName= parser.getName();
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                        if ("recipe".equals(nodeName))
                        {
                            Recip recip = new Recip();
                            recip.parse(parser,nodeName);
                            this.recipList.add(recip);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (!nodeName.equals(endTag))
                        {
                            break;
                        }
                        keepParseing = false;
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
