package com.myApk.db.news;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myApk.db.DBHelper;
import com.myApk.model.News;
import com.myApk.uitl.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/9/16.
 */
public class NewsDaoImpl implements INewsDao {
    private Context mContext;
    private static NewsDaoImpl instance;
    private DBHelper dbHelper;

    private NewsDaoImpl(Context context,String msisdn){
        this.mContext = context;
        dbHelper = DBHelper.getInstance(context);
    }

    public static NewsDaoImpl getInstance(Context context,String msisdn){
        if (instance == null){
            instance = new NewsDaoImpl(context,msisdn);
        }
        return  instance;
    }

    private ContentValues itemtoContentValues(News item) {
        ContentValues values = new ContentValues();
//        values.put(Column.NEWS_ID,item.);
        values.put(Column.TITLE,item.title);
        values.put(Column.URL,item.url);
        values.put(Column.PHOTOURL,item.photoUrl);
        values.put(Column.SOURCE,item.source);
        values.put(Column.DATE,item.date);
        values.put(Column.ABS,item.abs);
        return values;

    }

    private boolean checkHasRecord(String title) {
        if (StringUtil.isNotEmpty(title)){
            SQLiteDatabase db  = dbHelper.getReadableDatabase();
            String sql = "select * from "+TABLE_Name + " where " +Column.TITLE + "=?";
            String selection = Column.TITLE + "=?";
            String [] whereArgs = {title};
            Cursor cursor =null;
//            cursor = db.query(TABLE_Name,null,selection,whereArgs,null,null,null);
            cursor = db.rawQuery(sql, whereArgs);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    return true;
                }
            }
            try {
//
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (cursor != null){
                    cursor.close();
                }
            }
        }
        return false;
    }

    @Override
    public void saveNews(List<News> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (News info : list){
            ContentValues values = itemtoContentValues(info);
            if (checkHasRecord(info.title)){
                String whereClause = Column.TITLE + "=?";
                String [] whereArgs = new String []{info.title};
                db.update(TABLE_Name,values,whereClause,whereArgs);
            }else {
                db.insert(TABLE_Name,null,values);
            }
        }
    }


    @Override
    public List<News> getNews() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from " + TABLE_Name;
        Cursor cursor = null;
        List<News> list = new ArrayList<>();
        cursor= db.rawQuery(sql, null);
        if (cursor!= null){
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                News news = new News();
                news.title = cursor.getString(cursor.getColumnIndex(Column.TITLE));
                news.url = cursor.getString(cursor.getColumnIndex(Column.URL));
                news.photoUrl = cursor.getString(cursor.getColumnIndex(Column.PHOTOURL));
                news.source = cursor.getString(cursor.getColumnIndex(Column.SOURCE));
                news.date = cursor.getString(cursor.getColumnIndex(Column.DATE));
                news.abs = cursor.getString(cursor.getColumnIndex(Column.ABS));
                list.add(news);
            };
        }
        return list;
    }
}
