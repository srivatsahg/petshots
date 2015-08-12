package org.vatsag.utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * Common Functions all over the project
 * @author GCA4KOR
 *
 */


public class PhoneSpecifications {
	//public static PhoneSpecifications instance;
	private static int actualHt = 0;
	private static int phoneHt = 0;
	private static int actualWt = 0;
	private static float aspectRatio = 0;
	private static boolean callingEnabled = false;
	
	public static void getPhoneSpecifications(Activity activity)
	{
		//if(instance == null)
		{
			actualHt = computeActualHeight(activity);
			phoneHt = computePhoneHeight(activity);
			aspectRatio = computeAspectRatio(activity);
			actualWt = computeActualWidth(activity);
			callingEnabled = computeCallAbility(activity);
		}
		//return instance;
		
	}
	
	public static int getActualHeight()
	{
		return actualHt;
	}
	
	public static int getPhoneHeight()
	{
		return phoneHt;
	}
	
	public static int getActualWidth()
	{
		return actualWt;
	}
	
	public static float getAspectRatio()
	{
		return aspectRatio;
	}
	
	public static boolean getCallAbility()
	{
		return callingEnabled;
	}
	
	
	
	/**
	 * <b>GetActualHeight - get phone height and reduce notification bar height from that.</b> 
	 * @param activity
	 * @return int Actual Phone Height excluding Notification bar height
	 * @author gca4kor
	 */
	public static int computeActualHeight(Activity activity){
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int height = metrics.heightPixels;
        
        switch(metrics.densityDpi)
        {
	        case DisplayMetrics.DENSITY_LOW:
	        	height=height - 19;
               break;
	        case DisplayMetrics.DENSITY_MEDIUM:
	        	height=height - 25;
                break;
	        case DisplayMetrics.DENSITY_HIGH:
	        	height=height - 38;
                break;
	        case DisplayMetrics.DENSITY_XHIGH:
	        	height=height - 50;
                break;
        }
        
        return height<1?320:height;
    }
	

	/**
	 * <b>GetPhoneHeight - get phone height</b> 
	 * @param activity
	 * @return int Actual Phone Height
	 * @author gca4kor
	 */
	public static int computePhoneHeight(Activity activity){
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int height = metrics.heightPixels;
        return height<1?320:height;
    }
	
	public static float computeAspectRatio(Activity activity)
	{
		return (float)computeActualWidth(activity)/(float)computePhoneHeight(activity);
	    
	}
	
	public static boolean computeCallAbility(Activity activity)
	{
	   TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
	   if(telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE)
		   return false;
	   else
		   return true;
	   
//	   String sIMEI = telephonyManager.getDeviceId();
//	   mPrefs.EditPrefs("IMEI", sIMEI);

	}
	
	
	/**
	 * <b>GetActualWidth - get phone width</b> 
	 * @param activity
	 * @return int Actual Phone Width
	 * @author gca4kor
	 */
	public static int computeActualWidth(Activity activity){
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width=metrics.widthPixels;
		return width<1?320:width;
    }
	
}
