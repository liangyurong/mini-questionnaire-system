package com.lyr.qs.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.Map;

/**
 * 判空工具
 */
public class EmptyUtils {

    /**
     * 判断对象是否为空
     * @param obj 要判断的对象
     * @return 对象为null或空字符串返回true，否则返回false
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if(obj instanceof JSONObject){
            return ((JSONObject)obj).isEmpty();
        }
        if(obj instanceof JSONArray){
            return ((JSONArray)obj).isEmpty();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            return ((Object[]) obj).length == 0;
        }
        return false;
    }


}
