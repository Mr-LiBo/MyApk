package com.myApk.db.Book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myApk.db.DBHelper;
import com.myApk.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/8/7.
 */
public class IBookDao {

    public static final String TABLE_NAME = "Book";

    public interface Column{
        public static final String _id = "_id";
        public static final String name = "name";//名称
        public static final String desic = "desic";//描述
    }

       /**
          * 建表语句
          * */
    public static final   String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ("
            + Column._id + " Text PRIMARY KEY,"
            + Column.name + " Text,"
            +Column.desic +" Text)";


    public static final String sqlCreateTable="create table Book( _id integer primary key autoincrement,name text,desic integer)";

    public void add(Context context)
    {
        for (int i=1; i<3;i++) {
            ContentValues values = new ContentValues();
            values.put("name", "雄霸天下"+i);
            values.put("desic", "雄霸天下");
            DBHelper dbHelper = DBHelper.getInstance(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(TABLE_NAME, null, values);
        }
    }


    public List<Book> find(Context context)
    {
        List<Book> data = null;
        DBHelper dbHelper = DBHelper.getInstance(context);
        SQLiteDatabase db =  dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.getCount() > 0){
            data= new ArrayList<>();
            while (cursor.moveToNext())
            {
                Book book = new Book();
                book._id =  cursor.getInt(cursor.getColumnIndex(Column._id));
                book.name = cursor.getString(cursor.getColumnIndex(Column.name));
                book.desic = cursor.getString(cursor.getColumnIndex(Column.desic));
                data.add(book);
            }
        }
        db.close();
        return  data;
    }

}
