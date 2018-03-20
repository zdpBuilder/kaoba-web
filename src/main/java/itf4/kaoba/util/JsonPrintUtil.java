package itf4.kaoba.util;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Log4j
public class JsonPrintUtil {

    // 对象日期字段格式化处理
    public static void printObjDataJsonFormat(HttpServletResponse response, Object obj, String key) {
        response.reset();
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateTimeValueProcessor());
            json = json.fromObject(obj, jsonConfig);
            json.put(key, json);
            out.print(json.toString());
            log.debug(json.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 带key输出json对象
    public static void printObjDataWithKey(HttpServletResponse response, Object obj, String key) {
        response.reset();
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();
            json.put(key, obj);
            out.print(json.toString());
            log.debug(json.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 不带key输出json对象
    public static void printObjDateWithoutKey(HttpServletResponse response, Object obj) {
        response.reset();
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            json = json.fromObject(obj, jsonConfig);
            out.print(json.toString());
            log.debug(json.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 不带key输出json对象
    public static void printObjDataWithoutKey(HttpServletResponse response, Object obj) {
        response.reset();
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateTimeValueProcessor());
            json = json.fromObject(obj, jsonConfig);
            out.print(json.toString());
            log.debug(json.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 不带key输出 数组
    public static void printJsonArrayWithoutKey(HttpServletResponse response, Object obj) {
        response.reset();
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            JSONArray json = JSONArray.fromObject(obj);
            out.print(json.toString());
            log.debug(json.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
