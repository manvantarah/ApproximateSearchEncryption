package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class utils {
	public static String getdate()

	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String currentDate=dateFormat.format(date); //2016/11/16 12:08:43
		return currentDate;
		
	}
	public static String gettime()

	{
	
	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	Date date = new Date();
	String time=dateFormat.format(date); //2016/11/16 12:08:43
	return time;
	}
	
	public static void main(String[] args) {
		System.out.println(getdate()+" "+gettime());
	}
	
}