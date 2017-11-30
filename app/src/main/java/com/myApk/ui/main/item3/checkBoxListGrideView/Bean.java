package com.myApk.ui.main.item3.checkBoxListGrideView;

/**
 * Created by admin on 2015/10/8.
 */
public class Bean {
    private String title;
    /**标识是否可以删除*/
    private boolean canRemove = true;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCanRemove() {
        return canRemove;
    }

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public Bean(String title, boolean canRemove) {
        this.title = title;
        this.canRemove = canRemove;
    }

    public Bean() {
    }
}
