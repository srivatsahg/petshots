package org.vatsag.utils;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**@author dys1kor This class sets and gets values from the shared preferences 
 */
public class SharedPrefsManager 
{	
	private Context currentActivity;
	private SharedPreferences mSharedPrefs;	
	private boolean localPrefrenceFlag=false;
	String localPrefrenceString="";

	/** List of preferences
	 * ---------------------------------------------------------------------------------------------------------
	 * |Sl|KEY							|Description								|Source						|	
	 * ---------------------------------------------------------------------------------------------------------|
	 * |1 |PROTOCOL			 			|Communicated protocol						|Response from dongle		|
	 * |2 |COMM_STATUS			 		|Connection status							|Computed					|
	 * |3 |BT_DEVICE_ADDRESS 	 		|Device connected to						|Bluetooth adapter response	|
	 * |4 |PHONE_HEIGHT					|Display height of phone					|Obtained from Phone spec	|
	 * |5 |PHONE_WIDTH					|Display width of phone						|Obtained from Phone spec	|
	 * |6 |PHONE_DENSITY				|Display density of phone					|Obtained from Phone spec	|
	 * |7 |TABLET						|Is the device tablet?						|Obtained from Phone spec	|
	 * |8 |CALLING_ENABLED				|Is call option available? 					|Obtained from Phone spec	|
	 * |9 |DATA_SOURCE					|Source of data selected by user			|Entered by user			|
	 * |10|FIRST_TIME_LAUNCH			|Application launched for the first time	|Computed					|	 
	 * |11|WORKSHOP_NAME				|Workshop name entered by user				|Entered by user			|
	 * |12|WORKSHOP_MOBILE_NUMBER		|Workshop phone number entered by user		|Entered by user			|
	 * |13|WORKSHOP_EMAIL_ID			|Workshop email id entered by user			|Entered by user			|
	 * |14|VEHICLE_WEIGHT				|Weight of vehicle entered by user			|Entered by user			|
	 * |15|VEHICLE_HEIGHT				|Height of vehicle entered by user			|Entered by user			|
	 * |16|VEHICLE_WIDTH				|Width of vehicle entered by user			|Entered by user			|
	 * |17|FUEL_TYPE					|Fuel type selected by user					|Entered by user			|
	 * |18|BRAND_NAME					|Brand selected by user						|Entered by user			|
	 * |19|MODEL_NAME					|Model selected by user						|Entered by user			|
	 * |20|SUB_MODEL_NAME				|Sub-Model selected by user					|Entered by user			|
	 * |21|MOTOR_NUM					|Engine number selected by user				|Entered by user			|
	 * |22|DRIVE_TYPE					|Drive type selected by user				|Entered by user			|
	 * |23|RB_KEY						|RB key found based on selections			|Computed					|
	 * |24|POWER_MIN					|Power range - min value					|Web Service				|
	 * |25|POWER_MAX					|Power range - max value					|Web Service				|
	 * |26|TORQUE_MIN					|Torque range - min value					|Web Service				|
	 * |27|TORQUE_MAX					|Torque range - max value					|Web Service				|
	 * |28|CYLINDERS					|No. of cylinders							|Web Service				|
	 * |29|ENGINE_CAPACITY				|Engine capacity							|Web Service				|
	 * |30|WORKSHOP_DETAILS_VIEW_SET	|Maps shown in workshop details?			|Computed					|	 
	 * ---------------------------------------------------------------------------------------------------------|
	 */

	/**Constructor**/
	public SharedPrefsManager(Context callingActivity)
	{
		currentActivity = callingActivity;
	}
	public SharedPrefsManager(Context callingActivity,String shName)
	{
			currentActivity = callingActivity;
			localPrefrenceFlag=true;
			localPrefrenceString=shName;
	}
	public SharedPrefsManager(Context callingActivity,String shName,int i)
	{
		mSharedPrefs = callingActivity.getSharedPreferences(shName,Context.MODE_PRIVATE);
		currentActivity = callingActivity;
	}

	/**Edit value of the key specified 
	 * @param sKey         Key in preferences
	 * @param sOptionValue Value to be set to
	 */
	public void EditPrefs(String sKey,String sOptionValue)
	{
		if(localPrefrenceFlag)
			mSharedPrefs=currentActivity.getSharedPreferences(localPrefrenceString, Context.MODE_PRIVATE);
		else
			mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(currentActivity);
		SharedPreferences.Editor mSharedPrefEditor = mSharedPrefs.edit();
		mSharedPrefEditor.putString(sKey, sOptionValue);
		mSharedPrefEditor.commit();		
	}
	public void EditPrefs(HashMap<String, String> hmValue)
	{
		if(mSharedPrefs==null){
			if(localPrefrenceFlag)
				mSharedPrefs=currentActivity.getSharedPreferences(localPrefrenceString, Context.MODE_PRIVATE);
			else
				mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(currentActivity);
		}
		SharedPreferences.Editor mSharedPrefEditor = mSharedPrefs.edit();
		Object[] objArr = hmValue.keySet().toArray();
		for(Object key : objArr){
			mSharedPrefEditor.putString(key.toString(), hmValue.get(key));
		}
		mSharedPrefEditor.commit();	
		Log.i("SharedPrefsManager", "COMMITED OK");


	}
	/**Get value of the key specified 
	 * @param sKey         Key in preferences
	 * @param sOptionValue Default value to be returned
	 */
	public String GetPrefs(String sKey, String sDefault)
	{
		if(localPrefrenceFlag)
			mSharedPrefs=currentActivity.getSharedPreferences(localPrefrenceString, Context.MODE_PRIVATE);
		else
			mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(currentActivity);		
		return mSharedPrefs.getString(sKey, sDefault);
	}	
}
