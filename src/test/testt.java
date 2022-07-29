package test;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class testt {

	public static void main(String[] args) {
		 java.util.Date date = Calendar.getInstance().getTime();  
         DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
         String strDate = dateFormat.format(date);  
         System.out.println("Converted String: " + strDate);  

	}

}
