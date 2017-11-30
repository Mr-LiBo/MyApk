package com.myApk.logic.peopleLogic;

import com.myApk.model.People;

import java.io.InputStream;
import java.util.List;

public interface IPeopleParse {
    /**
     * 解析输入流，得到集合
     * @param is
     * @return
     * @throws Exception
     */
    public List<People> parse(InputStream is) throws Exception;

    /**
     * 序列化people对象集合，得到xml列式的字条串
     * @param peoples
     * @return
     * @throws Exception
     */
    public String serialize(List<People> peoples) throws Exception;
}
