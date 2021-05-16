package com.lb.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Auther: qiuxinfa
 * @Date: 2019/11/15
 * @Description: com.qxf.utils
 */
//封装返回结果
public class ResultUtil {
    public static String result(final Integer status,final String msg,final Object data,final Integer total) throws JSONException {
        JSONObject jsonObject = new JSONObject(){
            {
                put("status", status);
                put("msg", msg);
                put("data", data);
                put("total", total);
            }
        };
        return jsonObject.toString();
    }

    public static String result(final Integer status,final String msg,final Object data) throws JSONException {
        JSONObject jsonObject = new JSONObject() {
            {
                put("status", status);
                put("msg", msg);
                put("data", data);
            }
        };
        return jsonObject.toString();
    }

    public static String result(final Integer status,final String msg) throws JSONException {
        JSONObject jsonObject = new JSONObject() {
            {
                put("status", status);
                put("msg", msg);
            }
        };
        return jsonObject.toString();
    }
}
