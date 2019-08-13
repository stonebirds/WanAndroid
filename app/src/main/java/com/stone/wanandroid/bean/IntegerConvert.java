package com.stone.wanandroid.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

/**
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-07
 */
public class IntegerConvert implements PropertyConverter<List<Integer>, String> {
    @Override
    public List<Integer> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        // 先得获得这个，然后再typeToken.getType()，否则会异常
        TypeToken<List<Integer>> typeToken = new TypeToken<List<Integer>>() {
        };
        return new Gson().fromJson(databaseValue, typeToken.getType());
    }

    @Override
    public String convertToDatabaseValue(List<Integer> entityProperty) {
        if (entityProperty == null || entityProperty.size() == 0) {
            return null;
        } else {
            String sb = new Gson().toJson(entityProperty);
            return sb;

        }
    }
}
