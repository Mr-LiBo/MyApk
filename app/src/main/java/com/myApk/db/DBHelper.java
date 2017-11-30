package com.myApk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myApk.db.Book.IBookDao;
import com.myApk.db.Recipe.IRecipeDao;
import com.myApk.db.news.INewsDao;

/**
 * 数据库
 */
public class DBHelper extends SQLiteOpenHelper {


    /**
     * 数据库名
     */
    private static final String DATABASE_NAME = "FOODS.db";
    /**
     * 数据库版本
     */
    private static final int DB_VERSION = 1;//数据库版本
    private static final String UPGRADE_METHOD_NAME = "upgradeVersion";

    private static DBHelper dbHelper;

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    public DBHelper(Context context) {
        this(context, DATABASE_NAME, null, DB_VERSION);
        Log.i("foods","DBHelper");
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE FOODS_RECIPE ("
//                + "_ID INTEGER PRIMARY KEY,"
//                + "name TEXT,"
//                + "desic TEXT,"
//                + "price DOUBLE"
//                + ");");
        sqLiteDatabase.execSQL(IRecipeDao.sqlCREATE_TABLE);
        sqLiteDatabase.execSQL(IBookDao.sqlCreateTable);

        sqLiteDatabase.execSQL(INewsDao.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        if (newVersion > oldVersion) {
//
//
//            Class[] parameterTypes = new Class[]{SQLiteDatabase.class};
//            for (int i = oldVersion + 1; i <= newVersion; i++) {
//                try {
//                    Method upgradeMethod = this.getClass().getDeclaredMethod(
//                            UPGRADE_METHOD_NAME + i, parameterTypes);
//
//                    upgradeMethod.invoke(this, sqLiteDatabase);
//
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }


}
