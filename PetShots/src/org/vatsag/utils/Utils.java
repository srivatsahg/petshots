package org.vatsag.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import org.vatsag.R;

/**
 * Common Functions all over the project
 * @author Thanks to GCA4KOR
 *
 */
public class Utils {

	public static int getFontSize(int appHeight){
		Log.i("gana", "getFontSize - appHeight->"+appHeight);
		int fontSize;
		
		if(appHeight>1000)
		{
			fontSize=(int)(appHeight/42.75);
		}else if(appHeight>450){
			fontSize=(int)(appHeight/30.75);
		}else{
			fontSize=(int)(appHeight/28.75);
		}
		//fontSize=(int) (appHeight/23.36);
		return fontSize;
	}

	/**
	 * <b> getResourceName </b>
	 * @param activity - Activity Context
	 * @param id - Resource ID which we need to know the Name
	 * @return  Name of the resource.
	 */
	public static String getResourceName(Activity activity,int id){
		return activity.getResources().getResourceEntryName(id);
	}

    /*
	 *  Returns age in
	 *  years months and days format
	 * */
    public static String calculateExactAge(String strdob) {
        String difference;
        try{
            // TODO Strip the year, month and day from the string and send it to the getAge function

            SimpleDateFormat custDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date dob = custDateFormat.parse(strdob);
            Calendar today = Calendar.getInstance();
            Calendar cd = Calendar.getInstance();
            cd.setTime(dob);


            today.add(Calendar.YEAR, -cd.get(Calendar.YEAR));
            today.add(Calendar.MONTH, -cd.get(Calendar.MONTH));
            today.add(Calendar.DAY_OF_MONTH, -cd.get(Calendar.DAY_OF_MONTH) + 1);
            int y = today.get(Calendar.YEAR);
            int m = today.get(Calendar.MONTH);
            int d = today.get(Calendar.DAY_OF_MONTH) - 1;

            difference =  String.valueOf(y) +  " yrs : " + String.valueOf(m) + " months : " + String.valueOf(d) + " days";
        }
        catch(ParseException pex){
            return "--";
        }
        catch(IllegalArgumentException iex){
            return "--";
        }
        return difference;
    }

	/**
	 * <b> validateEMail - Validated the string is valid email id or not</b>
	 * @param emailString Email ID String to be validated
	 * @return boolean if emailString is valid
	 * @author gca4kor
	 */
	public static boolean validateEMail(String emailString){
		/*final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
		          "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +"\\@" +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
		          "(" +"\\." +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +")+");*/
		Pattern EMAIL_ADDRESS_PATTERN=android.util.Patterns.EMAIL_ADDRESS;
		return EMAIL_ADDRESS_PATTERN.matcher(emailString+"").matches();
	}
	
	/**
	 * <b> validatePhone - Validated the string is valid Phone number or not</b>
	 * @param phoneString Phone number String to be validated
	 * @return boolean if phoneString is valid
	 * @author gca4kor
	 */
	public static boolean validatePhoneNumber(String phoneString){
		/*final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
		          "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +"\\@" +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
		          "(" +"\\." +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +")+");*/
		Pattern EMAIL_ADDRESS_PATTERN=android.util.Patterns.PHONE;
		return EMAIL_ADDRESS_PATTERN.matcher(phoneString+"").matches();
	}	
}
