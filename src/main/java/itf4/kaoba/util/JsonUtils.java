package itf4.kaoba.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;

public class JsonUtils {

 
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 灏嗗璞¤浆鎹㈡垚json瀛楃涓层�
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 灏唈son缁撴灉闆嗚浆鍖栦负瀵硅薄
     * 
     * @param jsonData json鏁版嵁
     * @param clazz 瀵硅薄涓殑object绫诲瀷
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 灏唈son鏁版嵁杞崲鎴恜ojo瀵硅薄list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    public  static String listToJson(List<?> list) {
    	// 变量
    	StringBuffer jsonStr = new StringBuffer();
    	// 前提条件
    	if (null == list || 0 == list.size()) {
    	return null;
    	}
    	// class对象
    	Class<?> classType = list.get(0).getClass();
    	// 得到javabean的名字
    	String javaBeanClassName = classType.getSimpleName();
    	 
    	jsonStr.append("[");
    	// 此JavaBean中所声明的所有字段
    	Field[] fields = classType.getDeclaredFields();
    	for (int i = 0; i < list.size(); i++) {
    	jsonStr.append("{");
    	for (Field field : fields) {
    	// 得到字段名
    	String fieldName = field.getName();
    	field.setAccessible(true);
    	// 得到指定对象上此 Field 表示的字段的值
    	Object fieldValue;
    	try {
    	fieldValue = field.get(list.get(i));
    	jsonStr.append("\"").append(fieldName.toLowerCase()).append("\":").append("\"").append(fieldValue).append("\"").append(",");
    	} catch (IllegalArgumentException e) {
    	e.printStackTrace();
    	} catch (IllegalAccessException e) {
    	e.printStackTrace();
    	}
    	}
    	jsonStr.deleteCharAt(jsonStr.length() - 1);
    	jsonStr.append("},"); 
    	}
    	jsonStr.deleteCharAt(jsonStr.length() - 1);
    	jsonStr.append("]");
    	return jsonStr.toString();
    	}
}
