package utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String YYMMDD = "yyyy/MM/dd/hhmmss";
	
	 private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
	        @Override
	        protected DateFormat initialValue() {
	            return new SimpleDateFormat("yyyyMMddhhmmssSSS");
	        }
	    };
	   
	    public static Date parse(String dateStr) throws ParseException {
	        return threadLocal.get().parse(dateStr);
	    }

	    public static String format(Date date) {
	        return threadLocal.get().format(date);
	    }
	    
	    public static Timestamp timestamp(Date date) {
	        return new Timestamp(date.getTime());
	    }

	public static String getToday(Date time){
		SimpleDateFormat df = new SimpleDateFormat(YYMMDD);
		String ctime = df.format(time);
		return ctime;
	}
}
