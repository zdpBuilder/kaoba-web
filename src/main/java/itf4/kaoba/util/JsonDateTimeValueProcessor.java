package itf4.kaoba.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateTimeValueProcessor implements JsonValueProcessor {

    // private String date_format = "yyyy-MM-dd";
    private String format = "yyyy-MM-dd HH:mm:ss";


    public JsonDateTimeValueProcessor() {
        super();
    }

    public JsonDateTimeValueProcessor(String format) {
        super();
        this.format = format;
    }

  


    private Object process(Object value) {
        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            return sdf.format(value);
        }
        return value == null ? "" : value.toString();
    }

    
    public Object processArrayValue(Object paramObject, JsonConfig paramJsonConfig) {
        return process(paramObject);
    }

    public Object processObjectValue(String paramString, Object paramObject, JsonConfig paramJsonConfig) {
        return process(paramObject);
    }
	
}
