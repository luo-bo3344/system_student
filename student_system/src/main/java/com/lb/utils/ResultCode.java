package com.lb.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.jackson.JsonObjectDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码统一返回结果
 * */
public class ResultCode {
   /* //测试
    public static void main(String[] args) {
        JSONObject.toJSONString("luo");//fastJson
        System.out.println(result(EnumCode.OK, "修改学生信息", "{id:1,name:luo}", 5));
    }*/

    /**
     * 四个参数的返回结果
     *
     * */
    public static String result(final EnumCode status,final String msg,Object data,final Integer total){
        String result="";
        ObjectMapper mapper = new ObjectMapper();  //jackson
        Map json=new HashMap<>();
        json.put("status",status);
        json.put("mag",msg);
        json.put("data",data);
        json.put("total",total);
        try {
             result = mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 三个参数的返回结果
     * */
    public static String result(final Integer status,final String msg,Object data){
        String result="";
        ObjectMapper mapper = new ObjectMapper();  //jackson
        Map json=new HashMap<>();
        json.put("status",status);
        json.put("mag",msg);
        json.put("data",data);
        try {
            result = mapper.writeValueAsString(json);
            //String json = JSONObject.toJSONString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 两个参数的返回结果
     * */
    public static String result(final Integer status,final String msg){
        String result="";
        ObjectMapper mapper = new ObjectMapper();  //jackson
        Map json=new HashMap<>();
        json.put("status",status);
        json.put("mag",msg);
        try {
            result = mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  result;
    }

}
