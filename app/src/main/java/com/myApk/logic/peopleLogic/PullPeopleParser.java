package com.myApk.logic.peopleLogic;

import android.util.Xml;

import com.myApk.model.People;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * http://blog.csdn.net/liuhe688/article/details/6415593
 */
public class PullPeopleParser implements IPeopleParse
{
    @Override
    public List<People> parse(InputStream is) throws Exception
    {
        List<People> peoples = null;
        People people = null;
        //由android.util.Xml创建一个XmlPullParser实例
        XmlPullParser parser = Xml.newPullParser();
        //设置输入流，并指定输入流编码方式
        parser.setInput(is,"UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                    peoples = new ArrayList<>();
                    break;

                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("people"))
                    {
                        people = new People();
                    }else if (parser.getName().equals("id"))
                    {
                        eventType = parser.next();
                        people.setId(Integer.parseInt(parser.getText()));
                    }else if (parser.getName().equals("gender"))
                    {
                        eventType = parser.next();
                        people.setGender(parser.getText());
                    }else if (parser.getName().equals("name"))
                    {
                        eventType = parser.next();
                        people.setName(parser.getText());
                    }else if (parser.getName().equals("level"))
                    {
                        eventType = parser.next();
                        people.setLevel(Integer.parseInt(parser.getText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("people"))
                    {
                        peoples.add(people);
                        people = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return peoples;
    }

    @Override
    public String serialize(List<People> peoples) throws Exception
    {
        //由android.util.Xml创建一个XmlSerializer实例
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);
        serializer.startDocument("UTF-8", true);
        serializer.startTag("","peoples");
        for (People people : peoples)
        {
            serializer.startTag("","people");
            serializer.attribute("","id",people.getId()+"");

            serializer.startTag("","gender");
            serializer.text(people.getGender());
            serializer.endTag("","gender");

            serializer.startTag("","name");
            serializer.text(people.getName());
            serializer.endTag("","name");

            serializer.startTag("","level");
            serializer.text(people.getName());
            serializer.endTag("","level");

            serializer.endTag("","people");
        }
        serializer.endTag("","peoples");
        serializer.endDocument();
        return writer.toString();
    }
}
