package itf4.kaoba.util;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
   
	public static String DateToString(Date date,String dateString) {
		 SimpleDateFormat formatter = new SimpleDateFormat(dateString);
		return formatter.format(date);
	}
	public static Date StringToDate(String dateString,String Dateformat) throws Exception {
		 SimpleDateFormat formatter = new SimpleDateFormat(Dateformat);
			return formatter.parse(dateString);
	}
}
