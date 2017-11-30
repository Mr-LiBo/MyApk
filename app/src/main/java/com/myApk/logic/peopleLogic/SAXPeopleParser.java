package com.myApk.logic.peopleLogic;

import com.myApk.model.People;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Result;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

/**
 * SAX 解析
 */
public class SAXPeopleParser implements IPeopleParse
{

    @Override
    public List<People> parse(InputStream is) throws Exception
    {
        // 取得SAXParserFactory实例
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // 获取SAXParser
        SAXParser parser = factory.newSAXParser();
        // 实例化MyHandler
        MyHandler handler = new MyHandler();
        parser.parse(is, handler);
        return handler.getPeoples();
    }

    @Override
    public String serialize(List<People> peoples) throws Exception
    {
        //取得实例
        SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();
        //从factory获取TransformerHandler实例
        TransformerHandler handler = factory.newTransformerHandler();
        //从handler获取Transformer实例
        Transformer transformer =  handler.getTransformer();
        // 设置输出采用的编码方式
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
        // 是否自动添加额外的空白
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        // 是否忽略XML声明
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        handler.setResult(result);
        //代表命名空间的URI 当URI无值时 须置为空字符串
        String uri ="" ;
        //命名空间的本地名称(不包含前缀) 当没有进行命名空间处理时 须置为空字符串
        String localName = "";
        handler.startDocument();
        handler.startElement(uri,localName,"peoples",null);

        //负责存放元素的属性信息
        AttributesImpl attrs = new AttributesImpl();
        char [] ch = null;
        for (People people: peoples)
        {
            //清空属性列表
            attrs.clear();
            //添加一个名为id的属性(type影响不大,这里设为string)
            attrs.addAttribute(uri,localName,"id","string",String.valueOf(people.getId()));
            //开始一个people元素 关联上面设定的id属性
            handler.startElement(uri, localName, "people", attrs);

            //开始一个gender元素 没有属性
            handler.startElement(uri,localName,"gender",null);
            ch = String.valueOf(people.getGender()).toCharArray();
            //设置gender元素的文本节点
            handler.characters(ch,0,ch.length);
            handler.endElement(uri, localName, "gender");

            handler.startElement(uri,localName,"name",null);
            ch = String.valueOf(people.getName()).toCharArray();
            handler.characters(ch,0,ch.length);
            handler.endElement(uri, localName, "name");

            handler.startElement(uri, localName, "level", null);
            ch = String.valueOf(people.getName()).toCharArray();
            handler.characters(ch,0,ch.length);
            handler.endElement(uri, localName, "level");

            handler.endElement(uri,localName,"people");

//

        }

        handler.endElement(uri,localName,"peoples");
        handler.endDocument();
        return writer.toString();
    }

    /**
     * 事件处理器
     */
    private class MyHandler extends DefaultHandler
    {

        private List<People> peoples;

        private People people;

        private StringBuilder builder;

        public List<People> getPeoples()
        {
            return peoples;
        }

        @Override
        public void startDocument() throws SAXException
        {
            super.startDocument();
            peoples = new ArrayList<People>();
            builder = new StringBuilder();
        }

        //当执行文档时遇到起始节点，startElement方法将会被调用，我们可以获取起始节点相关信息；
        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException
        {
            super.startElement(uri, localName, qName, attributes);
            if (localName.equals("people"))
            {
                people = new People();
            }
            builder.setLength(0);//将字符长度设置为0 以便重新开始读取元素内的字符节点
        }

        //然后characters方法被调用，我们可以获取节点内的文本信息；
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            builder.append(ch,start,length);//将读取的字符数组追加到builder中
        }

        //然后characters方法被调用，我们可以获取节点内的文本信息；
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (localName.equals("id"))
            {
                people.setId(Integer.parseInt(builder.toString()));
            }else if (localName.equals("gender"))
            {
                people.setGender(builder.toString());
            }else if (localName.equals("name"))
            {
                people.setName(builder.toString());
            }else if (localName.equals("level"))
            {
                people.setLevel(Integer.parseInt(builder.toString()));
            }else if (localName.equals("people"))
            {
                peoples.add(people);
            }

        }
    }
}
