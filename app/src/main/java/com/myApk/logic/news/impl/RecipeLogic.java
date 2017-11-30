package com.myApk.logic.news.impl;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.myApk.db.Recipe.IRecipeDao;
import com.myApk.db.Recipe.RecipeDao;
import com.myApk.model.Recipe;

import java.util.List;

/**
 * Created by admin on 2015/8/4.
 */
public class RecipeLogic
{
    public static final int QRY_RECIPE_ALL_SUCCESS=0;
    public static final int ADD_RECIPE_ALL_SUCCESS=0;

   private static RecipeLogic recipeLogic=null;



    public static  RecipeLogic getInstance(){
        if(recipeLogic == null){
            recipeLogic= new RecipeLogic();
        }
        return recipeLogic;
    }

   public  List<Recipe> qryRecipe(Context context){
       IRecipeDao recipeDao = new RecipeDao(context);
       List<Recipe> lists = recipeDao.getRecipeAll();
       if(lists != null)
       {
           Message message = new Message();
           message.what=QRY_RECIPE_ALL_SUCCESS;
           message.obj = lists;

       }
       return lists;
   }

    public void addRecipe(Context context,Recipe recipe)
    {
        IRecipeDao recipeDao = RecipeDao.getInstance(context);
        boolean bl= recipeDao.addRecipe(recipe);
        if(bl){
            Message message = new Message();
            message.what = ADD_RECIPE_ALL_SUCCESS;
        }
    }

    public void deleteRecipe(Context context,Recipe recipe)
    {
        IRecipeDao recipeDao = RecipeDao.getInstance(context);
        recipeDao.deleteRecipe(recipe);
    }

    public void updateRecipe(Context context,Recipe newwRecipe)
    {
        Log.i("----->",newwRecipe.id + newwRecipe.name + newwRecipe.price);
        IRecipeDao recipeDao = RecipeDao.getInstance(context);
        Log.i("----->","recipeDao");
        boolean bl = recipeDao.update(newwRecipe);
        Log.i("----->",bl+"");
        if(bl){

        }
    }
}
