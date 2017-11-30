package com.myApk.ui.main.item3.sortListGrideViewItem;

import java.util.Comparator;

/**
 * Created by admin on 2015/9/28.
 */
public class PinyinComparator implements Comparator<SortModel>{
    @Override
    public int compare(SortModel o1, SortModel o2) {
        if (o1.sortLetters.equals("@") || o2.sortLetters.equals("#")) {
            return -1;
        } else if (o1.sortLetters.equals("#")
                || o2.sortLetters.equals("@"))
        {
            return 1;
        } else {
            return o1.sortLetters.compareTo(o2.sortLetters);
        }
    }
}
