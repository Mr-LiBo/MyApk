package com.myApk.ui.main.item3;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.myApk.R;
import com.myApk.logic.peopleLogic.IPeopleParse;
import com.myApk.logic.peopleLogic.PullPeopleParser;
import com.myApk.logic.zooParseXml.AnimalList;
import com.myApk.logic.zooParseXml.RecipeList;
import com.myApk.logic.zooParseXml.Zoo;
import com.myApk.model.People;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 解析
 */
public class PeopleParserActivity extends Activity implements View.OnClickListener
{
    private static final String TAG = "XML";

    private IPeopleParse parser ;
    private List<People>  peoples;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_parser);
        Button readBtn = (Button) findViewById(R.id.read);
        Button writeBtn = (Button) findViewById(R.id.write);
        Button readAndWrite = (Button) findViewById(R.id.readandwrite);
        Button diyread = (Button) findViewById(R.id.diyread);


        readBtn.setOnClickListener(this);
        writeBtn.setOnClickListener(this);
        readAndWrite.setOnClickListener(this);
        diyread.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.read:
                try {
                    InputStream is = getAssets().open("people.xml");
//                    parser = new SAXPeopleParser();
//                    parser = new DOMPeopleParser();
                    parser = new PullPeopleParser();
                    peoples = parser.parse(is);
                    for (People people : peoples)
                    {
                        Log.i(TAG,people.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.diyread:
                try {
                    InputStream is = getAssets().open("zoo.xml");
                    Zoo zoo = new Zoo();
                    zoo.parse(is);
                    AnimalList animalList = zoo.animalList;
                    for (int i=0;i<animalList.animalList.size();i++)
                    {
                        Log.i("LoG",animalList.animalList.get(i).name);
                        RecipeList recipeList = animalList.animalList.get(i).recipeList;
                        for (int j=0;j<recipeList.recipList.size();j++)
                        {
                            Log.i("LoG",recipeList.recipList.get(i).desic);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.write:
                try {
                    //获取到的路径为手机内存
                    File file = Environment.getExternalStorageDirectory();
                    // SD卡根目录路径
                    String path = file.getPath();

                    File createFolder = new File(path+ File.separator+"MyApk");
                    if (!createFolder.exists() && !createFolder.isDirectory())
                    {
                        createFolder.mkdirs();
                    }

                    File createFile = new File(createFolder.getPath()+"/people.xml");
                    if (!createFile.exists() && !createFile.isDirectory())
                    {
                        createFile.createNewFile();
                    }

                    String strXml = parser.serialize(peoples);

//                    FileOutputStream fos = openFileOutput("people.xml", Context.MODE_PRIVATE);
//                    fos.write(strXml.getBytes("UTF-8"));

                    FileWriter fw= new FileWriter(createFile.getPath(),false); //true表示追加文字,没有就是覆盖
                    fw.write(strXml);
                    fw.flush();
                    fw.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.readandwrite:
                FileReader fr = null;
                FileWriter fw = null;
                String filePath = Environment.getExternalStorageDirectory().getPath();
                try {
                    fr = new FileReader(filePath+"/MyApk/people.xml");
                    fw = new FileWriter(filePath+"/MyApk/temp.xml",false);
                    char [] buf = new char[1024];
                    int len =0;
                    while((len = fr.read(buf)) != -1 )
                    {
                        fw.write(buf,0,len);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (fr!= null)
                    {
                        try {
                            fr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (fw!= null)
                    {
                        try {
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

//    public void writeFile()
//    {
//
////      可以通过BufferedReader 流的形式进行流缓存，之后通过readLine方法获取到缓存的内容。
//        BufferedReader bre = null;
//        OutputStreamWriter pw = null;//定义一个流
//        try {
//            String file = "D:/test/test.xml";
//            bre = new BufferedReader(new FileReader(file));//此时获取到的bre就是整个文件的缓存流
//            pw = new OutputStreamWriter(new FileOutputStream("D:/New.xml"),"GBK");//确认流的输出文件和编码格式，此过程创建了“test.txt”实例
//            while ((str = bre.readLine())!= null) // 判断最后一行不存在，为空结束循环
//            {
//                if(str.indexOf("<end>")){//判断如果满足xml的条件就插入字符串
//                    pw.write(str);//将要写入文件的内容，可以多次write
//                    pw.write("插入的内容");//将要写入文件的内容，可以多次write
//                }
//            };
//            bre.close();
//            pw.close();//关闭流
////                        备注：文件流用完之后必须及时通过close方法关闭，否则会一直处于打开状态，直至程序停止，增加系统负担。
//
//        }
}
