package com.myApk.db.Recipe;

import com.myApk.model.Recipe;

import java.util.List;

/**
 * Created by admin on 2015/8/3.
 */
public interface IRecipeDao
{
    public static final String TABLE_NAME ="FOODS_RECIPE";

    public interface Column{
        public static final String _ID = "_id";
        public static final String NAME = "name";//名称
        public static final String DESIC = "desic";//描述
        public static final String PRICE = "price";//价格
    }

    public static  final String sqlCREATE_TABLE = "create table FOODS_RECIPE (_id integer ,name verchar(20),desic verchar(20),price double)";



    /**
     * 查询所在
     * @return
     */
    public List<Recipe> getRecipeAll();

    /**
     * 查询某个
     * @return
     */
    public List<Recipe> getRecipe(String id);

    /**
     * 增加
     */
    public boolean addRecipe(Recipe recipe);

    /**
     * 删除
     * @param recipe
     * @return
     */
    public boolean deleteRecipe(Recipe recipe);

    /**
     * 修改
     */
    public boolean update(Recipe recipe);
}
