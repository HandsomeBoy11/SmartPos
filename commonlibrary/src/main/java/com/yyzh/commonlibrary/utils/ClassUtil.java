package com.yyzh.commonlibrary.utils;


import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;

import com.yyzh.commonlibrary.base.NoViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtil<T> {

    public <T> Class<T> getViewModel(Object obj) {
        Class currentClass = obj.getClass();
        Class tClass = getGenericClass(currentClass, ViewModel.class);
        return (tClass != null && (tClass!=AndroidViewModel.class) && (tClass!= NoViewModel.class)) ? tClass : null;
    }

    private <T> Class<T> getGenericClass(Class klass, Class filterClass) {
        Type type = klass.getGenericSuperclass();
        if (type != null && type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType)type).getActualTypeArguments();
            Type[] arr = types;
            int var8 = types.length;

            for(int i = 0; i < var8; ++i) {
                Type t = arr[i];

                Class tClass = (Class)t;
                if (filterClass.isAssignableFrom(tClass)) {
                    return tClass;
                }
            }

            return null;
        } else {
            return null;
        }
    }

}
