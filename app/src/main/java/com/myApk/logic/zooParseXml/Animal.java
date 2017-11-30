package com.myApk.logic.zooParseXml;

import com.myApk.uitl.StringUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by admin on 2016/2/22.
 */
public class Animal
{
    public int id;
    
    public String gender;
    
    public String name;
    
    public int level;
    
    public RecipeList recipeList;
    
    public void parse(XmlPullParser parser, String endTag)
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
                        if ("id".equals(nodeName))
                        {
                            String nextStr = parser.nextText();
                            this.id = StringUtil.isNotEmpty(nextStr) ? Integer.parseInt(nextStr)
                                    : null;
                        }
                        else if ("gender".equals(nodeName))
                        {
                            String nextStr = parser.nextText();
                            this.gender = StringUtil.isNotEmpty(nextStr) ? nextStr
                                    : null;
                        }
                        else if ("name".equals(nodeName))
                        {
                            String nextStr = parser.nextText();
                            this.name = StringUtil.isNotEmpty(nextStr) ? nextStr
                                    : null;
                        }
                        else if ("level".equals(nodeName))
                        {
                            String nextStr = parser.nextText();
                            this.level = StringUtil.isNotEmpty(nextStr) ? Integer.parseInt(nextStr)
                                    : 0;
                        }else if ("recipes".equals(nodeName))
                        {
                            this.recipeList = new RecipeList();
                            this.recipeList.parse(parser,nodeName);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (!nodeName.equals(endTag))
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
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
}
