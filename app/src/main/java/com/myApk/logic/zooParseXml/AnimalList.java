package com.myApk.logic.zooParseXml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/2/22.
 */
public class AnimalList
{
    public List<Animal> animalList = new ArrayList<>();

    public  void parse(XmlPullParser parser,String endTag)
    {
        try
        {
            boolean keepParsing = true;
            while (keepParsing)
            {
                int eventType = parser.getEventType();
                String nodeName = parser.getName();
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                        if ("animal".equals(nodeName))
                        {
                            Animal animal = new Animal();
                            animal.parse(parser,nodeName);
                            this.animalList.add(animal);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (!nodeName .equals(endTag))
                        {
                            break;
                        }
                        keepParsing = false;
                }
                if (!keepParsing)
                {
                    continue;
                }
                parser.next();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
