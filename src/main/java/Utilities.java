

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utilities {
	
	public static String FormatDate(String inDate) {		
		if (inDate.length() == 9) {
			if (inDate.substring(1, 2).equals("/")) {
				return ("0"+inDate);
			} else {
				return (inDate.substring(0, 3)+"0"+inDate.substring(3, 9));
			}				
		}else if (inDate.length() == 8) {
			return ("0"+inDate.substring(0, 2)+"0"+inDate.substring(2, 8));
		}

		return inDate;
	}

	public static String GetTimeStamp(int days) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.hhmmss");
		Calendar cal = GregorianCalendar.getInstance();
		cal.add( Calendar.DAY_OF_YEAR, (days));
		Date date = cal.getTime();		

		return dateFormat.format(date);
	}
	
	public static String Get_mm_dd_yyyy_usa(int days) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = GregorianCalendar.getInstance();
		cal.add( Calendar.DAY_OF_YEAR, (days));
		Date date = cal.getTime();		

		return dateFormat.format(date);
	}
	
	public static String Get_mm_dd_yyyy_dash(int days) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = GregorianCalendar.getInstance();
		cal.add( Calendar.DAY_OF_YEAR, (days));
		Date date = cal.getTime();		

		return dateFormat.format(date);
	}

	public static String Get_yyyy_mm_dd_dash(int days) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = GregorianCalendar.getInstance();
		cal.add( Calendar.DAY_OF_YEAR, (days));
		Date date = cal.getTime();		

		return dateFormat.format(date);
	}

	
}