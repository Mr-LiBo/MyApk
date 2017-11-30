package com.myApk.db.Recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myApk.db.DBHelper;
import com.myApk.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/8/3.
 */
public class RecipeDao implements IRecipeDao
{
    private  static RecipeDao recipeDao;
    private Context context;
    private DBHelper dbHelper;

    public static IRecipeDao getInstance(Context context)
    {
        if(recipeDao == null)
        {
            recipeDao = new RecipeDao(context);
        }
        return  recipeDao;
    }

    public RecipeDao(Context context)
    {
        this.context = context;
        dbHelper = DBHelper.getInstance(context);
    }

    private List<Recipe>  cursorToListRecipe(List<Recipe> lists, Cursor cursor)
    {
        while (cursor.moveToNext())
       {
           Recipe recipe = new Recipe();
           recipe.id=cursor.getInt(cursor.getColumnIndex(Column._ID));
           recipe.name = cursor.getString(cursor.getColumnIndex(Column.NAME));
           recipe.desic = cursor.getString(cursor.getColumnIndex(Column.DESIC));
           recipe.price = cursor.getDouble(cursor.getColumnIndex(Column.PRICE));
           lists.add(recipe);
       }
        return lists;
    }



    @Override
    public List<Recipe> getRecipeAll() {
        List<Recipe> lists = new ArrayList<Recipe>();
        SQLiteDatabase db  = dbHelper.getReadableDatabase();
        Cursor cursor = null;


//        table：表名。相当于select语句from关键字后面的部分。如果是多表联合查询，可以用逗号将两个表名分开。
//        columns：要查询出来的列名。相当于select语句select关键字后面的部分。
//        selection：查询条件子句，相当于select语句where关键字后面的部分，在条件子句允许使用占位符“?”
//        selectionArgs：对应于selection语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常。
//        groupBy：相当于select语句group by关键字后面的部分 having：相当于select语句having关键字后面的部分
//        orderBy：相当于select语句order by关键字后面的部分，如：personid desc, age asc;
//        limit：指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分。

        cursor =db.query(TABLE_NAME, new String[]{Column._ID,Column.NAME,Column.DESIC,Column.PRICE},null,null ,null,null,null);



        if(null != cursor && cursor.moveToFirst())
        {
            lists= cursorToListRecipe(lists,cursor);
        }
        cursor.close();
        db.close();
        Log.i("--->"," getRecipeAll:"+lists.size());
        return lists;
    }

    @Override
    public List<Recipe> getRecipe( String id) {
        String whereClause = Column._ID + "=?";
        String [] whereArges = new String[]{id};
        List<Recipe> lists = new ArrayList<Recipe>();
        SQLiteDatabase db  = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        cursor =db.query(TABLE_NAME, new String[]{Column._ID,Column.NAME,Column.DESIC,Column.PRICE},Column._ID+"=?",whereArges ,null,null,null);

        String sql = "select * from " + TABLE_NAME + " where " + Column._ID + "=?";
        String[] whereArgs = {
                id
        };
        cursor = db.rawQuery(sql, whereArgs);;

        if(null != cursor && cursor.moveToFirst())
        {
            lists= cursorToListRecipe(lists,cursor);
        }
        return lists;
    }

    @Override
    public boolean addRecipe( Recipe recipe) {
        SQLiteDatabase db  = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Column._ID, recipe.id);
        values.put(Column.NAME, recipe.name);
        values.put(Column.DESIC, recipe.desic);
        values.put(Column.PRICE, recipe.price);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        Log.i("foods", "addRecipe");
        if(result >0){
            return true;
        }

        return false;

    }

    @Override
    public boolean deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int result;
        if(null != recipe.id+""){
            result = db.delete(TABLE_NAME, Column._ID+"=?", new String[]{recipe.id+""});
        }else{
            result = db.delete(TABLE_NAME, null,null);
        }
        if(result > 0){return true;}
        return  result > 0 ? true :false ;
    }

    @Override
    public boolean update(Recipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Column.NAME, recipe.name);
        values.put(Column.DESIC, recipe.desic);
        values.put(Column.PRICE,recipe.price);
        String where = Column._ID + " = ?";
        String [] whereValues={recipe.id+""};
        int result = db.update(TABLE_NAME, values,where,whereValues);
        if(result >0){return true;}
        return false;
    }
}
