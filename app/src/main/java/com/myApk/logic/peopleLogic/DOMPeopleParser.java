package com.myApk.logic.peopleLogic;

import com.myApk.model.People;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by admin on 2016/2/18.
 */
public class DOMPeopleParser implements IPeopleParse
{
    @Override
    public List<People> parse(InputStream is) throws Exception
    {
        List<People> peoples = new ArrayList<>();
        //获取实例
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //从factory获取DocumentBuilder 实例
        DocumentBuilder builder = factory.newDocumentBuilder();
        //解析输入流得到document实例
        Document doc = builder.parse(is);
        Element rootElement = doc.getDocumentElement();
        NodeList items = rootElement.getElementsByTagName("people");
        for (int i = 0;i <items.getLength();i++)
        {
            People people = new People();
            Node item = items.item(i);
            NodeList properties = item.getChildNodes();
            for (int j =0;j< properties.getLength();j++)
            {
                Node property = properties.item(j);
                String nodeName = property.getNodeName();
                if (nodeName.equals("id"))
                {
                    people.setId(Integer.parseInt(property.getFirstChild().getNodeValue()));
                }else if (nodeName.equals("gender"))
                {
                    people.setGender(property.getFirstChild().getNodeValue());
                }else if (nodeName.equals("name"))
                {
                    people.setName(property.getFirstChild().getNodeValue());
                }else if (nodeName.equals("level"))
                {
                    people.setLevel(Integer.parseInt(property.getFirstChild().getNodeValue()));
                }
            }
            peoples.add(people);
        }
        return peoples;
    }

    @Override
    public String serialize(List<People> peoples) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element rootElement = doc.createElement("people");
        for (People people : peoples)
        {
            Element peopleElement = doc.createElement("people");
            peopleElement.setAttribute("id", people.getId() + "");

            Element genderElement = doc.createElement("gender");
            genderElement.setTextContent(people.getName());
            peopleElement.appendChild(genderElement);

            Element nameElement = doc.createElement("name");
            nameElement.setTextContent(people.getName() + "");
            peopleElement.appendChild(nameElement);

            Element levelElement = doc.createElement("level");
            levelElement.setTextContent(people.getLevel() + "");
            peopleElement.appendChild(levelElement);

            rootElement.appendChild(peopleElement);
        }
        doc.adoptNode(rootElement);

        //取得transformerFacotory实例
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        //从transFactory获取Transformer实例
        Transformer transformer = transformerFactory.newTransformer();
        // 设置输出采用的编码方式
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
        // 是否自动添加额外的空白
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        // 是否忽略XML声明
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"no");

        StringWriter writer = new StringWriter();

        //表明文档来源是doc
        Source source = new DOMSource(doc);
        //表明目标结果writer
        Result result = new StreamResult(writer);
        //开始转换
        transformer.transform(source,result);
        return writer.toString();
    }
}
